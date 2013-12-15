/**
 * (c) 2013-2014 Kaden & Partner AG
 */
package economiesuisse
{
	import flash.display.Loader;
	import flash.events.Event;
	import flash.system.ApplicationDomain;
	import flash.system.Capabilities;
	
	import citrus.core.CitrusEngine;
	import citrus.core.starling.StarlingCitrusEngine;
	import citrus.core.starling.ViewportMode;
	import citrus.input.controllers.Keyboard;
	import citrus.utils.Mobile;
	
	import economiesuisse.controls.NBJoystick;
	import economiesuisse.lang.DE;
	import economiesuisse.lang.FR;
	import economiesuisse.levels.ALevel;
	import economiesuisse.levels.Level1;
	import economiesuisse.states.HighscoreState;
	import economiesuisse.states.InfoState;
	import economiesuisse.states.KpSplashState;
	import economiesuisse.states.MenuState;
	import economiesuisse.tools.EnhancedLevelManager;
	
	import feathers.themes.MetalWorksMobileTheme;
	
	import starling.core.Starling;
	import starling.display.Image;
	import starling.display.Sprite;
	
	
	[SWF(frameRate="60", backgroundColor="#000000")]
	//[ResourceBundle("R")]
	public class EconomieSuisseGameMobile extends StarlingCitrusEngine
	{
		
		private var loader:Loader;
		
		public var assetsManager:MobileAssetManager;
		public var isFinish:Boolean = false;
		//private var R:IResourceManager;
		
		private var joystick:NBJoystick;
		
		public var lvlManager:EnhancedLevelManager;
		private var joy:Sprite;
		
		protected var _isInstructing:Boolean = false;
		
		
		
		override protected function handleAddedToStage(e:Event):void
		{
			super.handleAddedToStage(e);
			var aspect:Number = stage.fullScreenWidth / stage.fullScreenHeight;
			var expectedAspect:Number = Constants.STAGE_WIDTH / Constants.STAGE_HEIGHT;
			
			// if needed, we can use letterboxed for wider aspects:
			//_viewportMode = (aspect < expectedAspect?ViewportMode.LETTERBOX : ViewportMode.FULLSCREEN);
			
			_viewportMode = ViewportMode.FULLSCREEN;

			_baseWidth = Constants.STAGE_WIDTH;
			_baseHeight = Constants.STAGE_HEIGHT;
			_assetSizes = [1,2,4];
			
			setUpStarling( Constants.DEBUG,0 );
			Starling.multitouchEnabled = true;
			onPlayingChange.add(playingChanged);
		}
		
		override public function handleStarlingReady():void {
			new MetalWorksMobileTheme();
			this.assetsManager = new MobileAssetManager(this,scaleFactor);
			CitrusEngine.getInstance().input.keyboard.onKeyDown.add(handleKeypress);
			
			// Displaying splash screen while loading assets:
			var splashState:KpSplashState = new KpSplashState;
			state = splashState;
			this.assetsManager.progressStage1.add(splashState.onAssetProgress);
			this.assetsManager.progressStage1.add(onAssetStage1Progress);
			this.assetsManager.progressStage2.add(onAssetStage2Progress);
			splashState.splashDone.add(onAppReady);
			this.assetsManager.loadAssetsStage1();
			DataManager.instance.getGlobalScore();
		}
		
		protected function onAssetStage1Progress(ratio:Number):void {
		}
		
		protected function onAssetStage2Progress(ratio:Number):void {
			if (state is MenuState) {
				(state as MenuState).updateProgress(ratio);
			}
		}
		
		private function handleKeypress(keyCode:uint,keyLocation:Number):void
		{
			switch (keyCode){
				case Keyboard.P:	
					playing=!playing
					break;
			}
		}
		
		private function playingChanged(isRunning:Boolean):void
		{
			if (_isInstructing){
				return;
			}
			trace("playing change signal, value",isRunning);
			if (isRunning) {
				onResume();
			} else if (isFinish) {
				onFinish();
			} else {
				onPaused();
			}
		}
		
		private function onResume():void
		{
			if (_isInstructing){
				return;
			}
			trace("onResume");
			if (lvlManager && lvlManager.currentLevel)
				(lvlManager.currentLevel as ALevel).onUnPause()
		}
		
		private function onFinish():void
		{
			trace("finished");
		}
		
		private function onPaused():void
		{
			if (_isInstructing){
				return;
			}
			trace("onPaused");
			if (lvlManager && lvlManager.currentLevel)
				(lvlManager.currentLevel as ALevel).onPause();
		}
		
		public function onAppReady():void {
			trace("All assets loaded, good to go!");
			
			lvlManager = new EnhancedLevelManager(ALevel);
			lvlManager.enableSwfCaching = true;
			lvlManager.applicationDomain = ApplicationDomain.currentDomain; // to be able to load your SWF level on iOS
			lvlManager.onLevelChanged.add(_onLevelChanged);
			lvlManager.levels = [[Level1, Constants.levels.ONE]];
			
			setUpMobileControls();
			showMainMenu();
		}
		
		public function setUpMobileControls():void {
			if (Mobile.isAndroid() || Mobile.isIOS()) {
				joy = new starling.display.Sprite();
				Starling.current.stage.addChild(joy);
				new NBJoystick("joy", {
					container: joy,
					viewknob: new Image(assetsManager.getTexture("joystickKnob")),
					viewback: new Image(assetsManager.getTexture("joystickCenter")),
					marginTop: 100
				});
			}
		}
		
		public function showMobileControls():void {
			if (joy) {
				joy.visible = true;
			}
		}
		
		public function hideMobileControls():void {
			if (joy) {
				joy.visible = false;
			}
		}
		
		public function startFirstLevel():void {
			isFinish = false;
			lvlManager.gotoLevel(0);
		}
		
		private function _onLevelChanged(lvl:ALevel):void {
			trace("onLevelChanged called");
			isFinish = false;
			playing = true;
			lvl.lvlEnded.addOnce(_nextLevel);
			lvl.restartLevel.addOnce(restartLevel);
			state = lvl;
		}
		
		private function _nextLevel():void {
			trace ("lvlEnded signal");
			assetsManager.stopAllSounds();
			// Do we have reached the last level? so go to main menu, else go to next level:
			if (lvlManager.currentIndex == lvlManager.levels.length - 1) {
				showMainMenu();	
			} else {
				lvlManager.gotoLevel(lvlManager.currentIndex + 1);
			}
		}
		
		public function restartLevel():void {
			trace("restartLevel signal");
			isFinish = false;
			lvlManager.gotoLevel(lvlManager.currentIndex);
		}
		
		/**
		 * Gibt den String mit dem entsprechenden Key aus der passenden Sprach-konstanten-Klasse aus (Default ist "de")
		 */
		public function getString(token:String):String {
			/*if (R == null) R = ResourceManager.getInstance();
			return R.getString("R", token);*/
			var ret:String = "";
			switch (Capabilities.language){
				case "de": 
					ret = DE.STRINGS[token];
					break;
				case "fr":
					ret = FR.STRINGS[token];
					break;	
				default:
					ret = DE.STRINGS[token];
			}
			return ret;
		}
		
		protected function onPlayBtn():void {
			if (assetsManager.stage2IsLoaded) {
				startGameFromMainmenu();
			}
		}
		
		protected function startGameFromMainmenu():void {
			this.sound.crossFade(Constants.sounds.MAIN_THEME,"",1);
			this.lvlManager.gotoLevel(0);		
		}
		
		protected function onHighscoreBtn():void {
			showHighscore();
		}
		
		protected function onInfoBtn():void {
			showInfoScreen();
		}
		
		protected function onHighscoreBackBtn():void {
			showMainMenu();
		}
		
		protected function onInfoBackBtn():void {
			showMainMenu();
		}
		
		protected function showHighscore():void {
			var highscoreState:HighscoreState = new HighscoreState();
			highscoreState.backBtnTriggered.add(onHighscoreBackBtn);
			state = highscoreState;
		}
		
		protected function showInfoScreen():void {
			var infoState:InfoState = new InfoState();
			infoState.backBtnTriggered.add(onInfoBackBtn);
			state = infoState;
		}
		
		protected function showMainMenu():void {
			hideMobileControls();
			var menuState:MenuState = new MenuState();
			menuState.highscoreBtnTriggered.add(onHighscoreBtn);
			menuState.startBtnTriggered.add(onPlayBtn);
			menuState.infoBtnTriggered.add(onInfoBtn);
			state = menuState;
		}
		
		public function get isInstructing():Boolean
		{
			return _isInstructing;
		}
		
		public function set isInstructing(value:Boolean):void
		{
			_isInstructing = value;
		}
	}
}

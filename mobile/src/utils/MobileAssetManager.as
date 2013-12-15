/**
 * (c) 2013-2014 Alexander Schenkel
 */
package utils
{
	//import flash.filesystem.File; <<--- DOES ONLY WORK WITHIN AIR APP
	import flash.system.Capabilities;
	
	import citrus.core.CitrusEngine;
	import citrus.sounds.CitrusSoundGroup;
	import citrus.sounds.CitrusSoundInstance;
	
	import org.osflash.signals.Signal;
	
	import starling.text.BitmapFont;
	import starling.text.TextField;
	import starling.textures.Texture;
	import starling.utils.AssetManager;
	import starling.utils.formatString;
	
	
	public class MobileAssetManager extends AssetManager
	{
		private var _isLoadedStage1:Boolean = false;
		private var _isLoadedStage2:Boolean = false;
		private var _ce:CitrusEngine;
		
		public var loadedStage1:Signal;
		public var loadedStage2:Signal;
		public var progressStage1:Signal;
		public var progressStage2:Signal;
		
		public function MobileAssetManager(citrusEngine:CitrusEngine, scaleFactor:Number=1)
		{
			super(scaleFactor,false);
			_ce = citrusEngine;
			loadedStage1 = new Signal();
			loadedStage2 = new Signal();
			progressStage1 = new Signal(Number);
			progressStage2 = new Signal(Number);
			
			var texture:Texture = Texture.fromBitmap(new Constants.UnispaceBitmapTexture());
			var xml:XML = XML(new Constants.UnispaceBitmapXml());
			
			TextField.registerBitmapFont(new BitmapFont(texture,xml));
		}
		
		public function get stage1IsLoaded():Boolean {
			return _isLoadedStage1;
		}
		
		public function get stage2IsLoaded():Boolean {
			return _isLoadedStage2;
		}
		
		public function loadAssetsStage1(): void {
			//var appDir:File = File.applicationDirectory;
			trace(formatString("MobileAssetManager: Using {0}x scale",scaleFactor));
			verbose = Capabilities.isDebugger;
			enqueueStage1();
			
			loadQueue(onProgressStage1);
		}
		
		public function loadAssetsStage2():void {
			enqueueCommon();
			enqueue1x();
			enqueue2x();
			enqueue4x();
			loadQueue(onProgressStage2);
		}
		
		protected function enqueueStage1():void {
			enqueue(
				formatString("./images/{0}x/ui_elements.xml",scaleFactor),
				formatString("./images/{0}x/ui_elements.png",scaleFactor)
				/*
				formatString("./images/{0}x/sprites.xml",scaleFactor),
				formatString("./images/{0}x/sprites.png",scaleFactor),
				formatString("./images/{0}x/menu_images.xml",scaleFactor),
				formatString("./images/{0}x/menu_images.png",scaleFactor),
				formatString("./sound/{0}.mp3",Constants.sounds.MAIN_THEME)
				*/
			);
		}
		
		protected function enqueueCommon():void {
			enqueue(
				formatString("./images/{0}x/stones.xml",scaleFactor),
				formatString("./images/{0}x/stones.png",scaleFactor)
				/*
				formatString("./images/{0}x/backgrounds_1.xml",scaleFactor),
				formatString("./images/{0}x/backgrounds_1.png",scaleFactor),
				
				formatString("./images/{0}x/animations.xml",scaleFactor),
				formatString("./images/{0}x/animations.png",scaleFactor),
				
				formatString("./images/{0}x/static_background.xml",scaleFactor),
				formatString("./images/{0}x/static_background.png",scaleFactor),
				
				
				// Sound
				formatString("./sound/{0}.mp3",Constants.sounds.GAME_MUSIC),
				//formatString("./sound/{0}.mp3",Constants.sounds.METAL_GAME_MUSIC),
				//formatString("./sound/{0}.mp3",Constants.sounds.MONKEY_GAME_MUSIC),
				formatString("./sound/{0}.mp3",Constants.sounds.BOOST_GAME_MUSIC),
				formatString("./sound/{0}.mp3",Constants.sounds.SFX_GOAL),
				formatString("./sound/{0}.mp3",Constants.sounds.SFX_JUMP),
				formatString("./sound/{0}.mp3",Constants.sounds.SFX_APPLE_BITE1),
				formatString("./sound/{0}.mp3",Constants.sounds.SFX_APPLE_BITE2),
				formatString("./sound/{0}.mp3",Constants.sounds.SFX_APPLE_BITE3),
				formatString("./sound/{0}.mp3",Constants.sounds.SFX_BIG_APPLE),
				formatString("./sound/{0}.mp3",Constants.sounds.SFX_BAD_APPLE),
				formatString("./sound/{0}.mp3",Constants.sounds.SFX_ENEMY_TOUCH_1),
				formatString("./sound/{0}.mp3",Constants.sounds.SFX_ENEMY_TOUCH_2),
				formatString("./sound/{0}.mp3",Constants.sounds.SFX_PAUSE_ON),
				formatString("./sound/{0}.mp3",Constants.sounds.SFX_STEP_SAND),
				formatString("./sound/{0}.mp3",Constants.sounds.SFX_STEP_GRASS),
				formatString("./sound/{0}.mp3",Constants.sounds.SFX_EVIL_LAUGH),
				formatString("./sound/{0}.mp3",Constants.sounds.SFX_HALLELUJAH),
				formatString("./sound/{0}.mp3",Constants.sounds.SFX_MWAP)
				*/
			)
		}
		
		protected function enqueue1x():void {
			if (scaleFactor == 1) {
			}
		}
		
		protected function enqueue2x():void {
			if (scaleFactor == 2) {
			}
		}
		
		protected function enqueue4x():void {
			if (scaleFactor == 4) {
				enqueue(
					/*
					formatString("./images/{0}x/backgrounds_2.xml",scaleFactor),
					formatString("./images/{0}x/backgrounds_2.png",scaleFactor)
					*/
				);
			}
		}
		
		private function onProgressStage1(ratio:Number):void {
			progressStage1.dispatch(ratio);
			if (ratio === 1.0) {
				_isLoadedStage1 = true;
				initializeSoundsStage1();
				loadedStage1.dispatch();
				purgeQueue();
				loadAssetsStage2();
				
			}
		}
		
		private function onProgressStage2(ratio:Number):void {
			progressStage2.dispatch(ratio);
			if (ratio === 1.0) {
				_isLoadedStage2 = true;
				initializeSoundsStage2();
				loadedStage2.dispatch();
			}
		}
		
		private function initializeSoundsStage1():void {
			//addCitrusSoundFromSoundManager(Constants.sounds.MAIN_THEME, {permanent: true, loops: -1, group: CitrusSoundGroup.UI});
		}
		
		private function initializeSoundsStage2():void {
			CitrusSoundInstance.onNewChannelsUnavailable = CitrusSoundInstance.REMOVE_LAST_PLAYED;
			_ce.sound.getGroup(CitrusSoundGroup.BGM).volume = 0.4;
			/*
			addCitrusSoundFromSoundManager(Constants.sounds.GAME_MUSIC, {permanent: true, loops: -1, group: CitrusSoundGroup.BGM});
			//addCitrusSoundFromSoundManager(Constants.sounds.METAL_GAME_MUSIC, {permanent: true, loops: -1, group: CitrusSoundGroup.BGM});
			//addCitrusSoundFromSoundManager(Constants.sounds.MONKEY_GAME_MUSIC, {permanent: true, loops: -1, group: CitrusSoundGroup.BGM});
			addCitrusSoundFromSoundManager(Constants.sounds.BOOST_GAME_MUSIC, {permanent: false, loops: -1, group: CitrusSoundGroup.BGM});
			addCitrusSoundFromSoundManager(Constants.sounds.SFX_GOAL);
			addCitrusSoundFromSoundManager(Constants.sounds.SFX_JUMP);
			addCitrusSoundFromSoundManager(Constants.sounds.SFX_APPLE_BITE1);
			addCitrusSoundFromSoundManager(Constants.sounds.SFX_APPLE_BITE2);
			addCitrusSoundFromSoundManager(Constants.sounds.SFX_APPLE_BITE3);
			addCitrusSoundFromSoundManager(Constants.sounds.SFX_BIG_APPLE);
			addCitrusSoundFromSoundManager(Constants.sounds.SFX_BAD_APPLE);
			addCitrusSoundFromSoundManager(Constants.sounds.SFX_ENEMY_TOUCH_1);
			addCitrusSoundFromSoundManager(Constants.sounds.SFX_ENEMY_TOUCH_2);
			addCitrusSoundFromSoundManager(Constants.sounds.SFX_PAUSE_ON);
			addCitrusSoundFromSoundManager(Constants.sounds.SFX_STEP_SAND, {loops:-1});
			addCitrusSoundFromSoundManager(Constants.sounds.SFX_STEP_GRASS, {loops:-1});
			addCitrusSoundFromSoundManager(Constants.sounds.SFX_EVIL_LAUGH);
			addCitrusSoundFromSoundManager(Constants.sounds.SFX_HALLELUJAH);
			addCitrusSoundFromSoundManager(Constants.sounds.SFX_MWAP);
			*/
		}
		
		/**
		 * Adds a sound to the citrus engine, getting the sound from the Starling sound manager.
		 * Adds it to the default sound group SFX if no options specified.
		 */
		private function addCitrusSoundFromSoundManager(id:String, options:Object = null):void {
			options = options || {};
			options.group = options.group || CitrusSoundGroup.SFX; 
			options.sound = this.getSound(id);
			_ce.sound.addSound(id,options);
		}
		
		
		
		public function get isLoadedStage1():Boolean {
			return this._isLoadedStage1;
		}
		
		public function get isLoadedStage2():Boolean {
			return this._isLoadedStage2;
		}
		
		public function play(soundName:String):void {
			_ce.sound.setVolume(soundName,1);
			_ce.sound.playSound(soundName);
		}
		
		public function pauseSound(soundName:String):void {
			_ce.sound.pauseSound(soundName);			
		}
		
		public function resumeSound(soundName:String):void {
			_ce.sound.resumeSound(soundName);
		}
		
		public function stopSound(soundName:String):void {
			_ce.sound.stopSound(soundName);
		}
		
		public function stopAllSounds():void {
			_ce.sound.stopAllPlayingSounds();
		}
	}
}
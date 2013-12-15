/**
 * (c) 2013-2014 Alexander Schenkel
 */
package states
{
	
	
	
	import flash.text.TextFormat;
	
	import citrus.core.starling.StarlingState;
	
	import feathers.controls.Button;
	import feathers.controls.ProgressBar;
	
	import org.osflash.signals.Signal;
	
	import starling.core.Starling;
	import starling.display.Image;
	import starling.events.Event;
	import starling.text.TextField;
	import starling.utils.Color;
	import starling.utils.HAlign;
	
	import ui.PlayBtn;
	
	import utils.MobileAssetManager;
	
	public class MenuState extends StarlingState
	{
		protected var assets:MobileAssetManager;
		private var progress:ProgressBar;
		private var playBtn:PlayBtn;
		
		public var highscoreBtnTriggered:Signal;
		public var infoBtnTriggered:Signal;
		public var startBtnTriggered:Signal;
		
		public function MenuState()
		{
			super();
			assets = (_ce as SensitiveRevival).assetsManager;
			highscoreBtnTriggered = new Signal();
			infoBtnTriggered = new Signal();
			startBtnTriggered = new Signal();
		}
		
		override public function initialize():void {
			super.initialize();
			stage.color = Color.WHITE;
			Starling.current.nativeStage.color = Color.WHITE;
			
			//assets.play(Constants.sounds.MAIN_THEME);
			
			
			playBtn = new PlayBtn();
			
			playBtn.triggered.add(onPlayBtnTriggered);
			playBtn.x = (stage.stageWidth + playBtn.width) / 2;
			playBtn.y = (this.stage.stageHeight - playBtn.height) / 2;
			this.addChild( playBtn );
			
			
			
			//Highscore-Btn
			/*
			var highscoreBtn:HighscoreBtn = new HighscoreBtn();
			highscoreBtn.triggered.add(onHighscoreBtnTriggered);
			highscoreBtn.pivotX = highscoreBtn.width;
			highscoreBtn.x = stage.stageWidth - 5;
			highscoreBtn.y = 5;
			addChild(highscoreBtn);
			
			//Info-Btn
			var infoBtn:InfoBtn = new InfoBtn();
			infoBtn.triggered.add(onInfoBtnTriggered);
			infoBtn.pivotX = infoBtn.width;
			infoBtn.x = stage.stageWidth - 5;
			infoBtn.y = highscoreBtn.height + 15;
			addChild(infoBtn);
			*/
			
			//var gameEndedPopUp:GameEndedPopUp = new GameEndedPopUp();
			//gameEndedPopUp.show(300,15320);
			
			progress = new ProgressBar();
			progress.minimum = 0;
			progress.maximum = 1;
			progress.width = stage.stageWidth;
			progress.height = 5;
			progress.x = 0;
			progress.y = stage.stageHeight - progress.height;
			addChild(progress);
			
			if ((_ce as SensitiveRevival).assetsManager.isLoadedStage2) {
				updateProgress(1.0);
			}
		}
		
		
		public function onPlayBtnTriggered():void {
			startBtnTriggered.dispatch();
		}
		
		protected function onHighscoreBtnTriggered():void {
			highscoreBtnTriggered.dispatch();
		}
		
		protected function onInfoBtnTriggered():void {
			infoBtnTriggered.dispatch();
		}
		
		public function updateProgress(ratio:Number):void {
			if (progress) {
				trace("progress stage 2:",ratio);
				progress.value = ratio;
				if (ratio == 1.0) {
					progress.removeFromParent(true);
				}
			}
			if (playBtn && ratio == 1.0) {
				//playBtn.label = (_ce as SensitiveRevival).getString("start");
			}
		}
	}
}
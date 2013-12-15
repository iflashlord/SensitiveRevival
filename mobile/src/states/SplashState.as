/**
 * (c) 2013-2014 Kaden & Partner AG
 */
package states
{
	import citrus.core.starling.StarlingState;
	
	import feathers.controls.ProgressBar;
	
	import org.osflash.signals.Signal;
	
	import starling.core.Starling;
	import starling.display.Image;
	import starling.utils.Color;
	
	import utils.MobileAssetManager;
	
	public class SplashState extends StarlingState
	{
		public var splashDone:Signal;
		
		protected var assets:MobileAssetManager;
		
		private var logo:Image;
		private var progress:ProgressBar;
		private var startTime:Number;
		
		public function SplashState()
		{
			super();
			splashDone = new Signal();
			assets = (_ce as SensitiveRevival).assetsManager;
			
		}
		
		override public function initialize():void {
			super.initialize();
			stage.color = Color.BLACK;
			
			progress = new ProgressBar();
			progress.minimum = 0;
			progress.maximum = 1;
			progress.width = stage.stageWidth;
			progress.height = 5;
			progress.x = 0;
			progress.y = stage.stageHeight - progress.height;
			addChild(progress);
			
		}
		
		
		public function onAssetProgress(ratio:Number):void {
			if (progress) {
				progress.value = ratio;
			}
			if (ratio == 1.0) {
				var actTime:Number = new Date().getTime();
				var waitTime:Number = Math.max(5000-(actTime - startTime),0);
				
				if (!Constants.DEBUG && waitTime > 0) {
					Starling.juggler.delayCall(function():void{
						splashDone.dispatch();
					},waitTime/1000.0)
				} else {
					splashDone.dispatch();
				}
			}
		}
	}
}
package states
{
	import flash.filters.BitmapFilter;
	import flash.filters.BitmapFilterQuality;
	import flash.filters.DropShadowFilter;
	
	import citrus.core.starling.StarlingState;
	import citrus.objects.CitrusSprite;
	
	import sprites.Background;
	
	import starling.display.Image;
	import starling.filters.BlurFilter;
	
	public class TestState extends StarlingState
	{
		private var assets:MobileAssetManager;
		
		public function TestState(assets:MobileAssetManager)
		{
			super();
			this.assets = assets;
		}
		
		override public function initialize():void {
			super.initialize();
			trace(assets.getTexture("bg").scale,stage.stageWidth,stage.stageHeight);
			//addChild(new Image(assets.getTexture("bg")));
			
			/*
			var block:Quad = new Quad(40,40,Color.RED);
			block.alpha = 0.5;
			addChild(block);
			*/
			var spr:Background = new Background("back");
			add(spr);
			
			for (var y:int = 1; y < 7; y++) {
				for (var i:int = 0; i < 10; i++) {
					var view:Image = new Image(assets.getTexture("stone-std-01"));
					view.filter = BlurFilter.createDropShadow(20.0); 
					var block:CitrusSprite = new CitrusSprite("stone-std-01",{
						view: view,
						y: y*Constants.STONE_WIDTH,
						x: i*Constants.STONE_WIDTH
					});
					add(block);
				}			
			}
		}
		
		override public function update(timeDelta:Number):void {
			super.update(timeDelta);
		}
		
		
		private function getBitmapFilter():BitmapFilter {
			var color:Number = 0x000000;
			var angle:Number = 45;
			var alpha:Number = 0.8;
			var blurX:Number = 8;
			var blurY:Number = 8;
			var distance:Number = 15;
			var strength:Number = 0.65;
			var inner:Boolean = false;
			var knockout:Boolean = false;
			var quality:Number = BitmapFilterQuality.HIGH;
			return new DropShadowFilter(distance,
				angle,
				color,
				alpha,
				blurX,
				blurY,
				strength,
				quality,
				inner,
				knockout);
		}
	}
}
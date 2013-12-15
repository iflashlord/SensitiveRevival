package sprites
{
	import citrus.objects.CitrusSprite;
	
	import feathers.display.TiledImage;
	
	import starling.textures.Texture;
	
	public class Background extends CitrusSprite
	{
		public function Background(name:String, params:Object = null) {
			super(name, params);
		}
		
		override public function initialize(poolObjParams:Object = null):void {
			super.initialize(poolObjParams);
			var bgTexture:Texture = (_ce as SensitiveRevival).assets.getTexture("bg");
			
			var bgImage:TiledImage = new TiledImage(bgTexture);
			bgImage.width = _ce.stage.stageWidth + Constants.STONE_WIDTH;
			bgImage.height = _ce.stage.stageWidth;
			view = bgImage;
			updateCallEnabled = true;
		}
		
		override public function update(timeDelta:Number):void {
			super.update(timeDelta);
			view.pivotX += (20*timeDelta);
			if (view.pivotX > Constants.STONE_WIDTH) {
				view.pivotX = view.pivotX - Constants.STONE_WIDTH;
			}
		}
	}
}
package sprites.stones
{
	import citrus.objects.CitrusSprite;
	
	import org.osflash.signals.Signal;
	
	public class Stone extends CitrusSprite
	{
		public var destroyed:Signal;
		
		public function Stone(name:String, params:Object=null) {
			super(name, params);
			destroyed = new Signal(Stone);
			touchable = false;
		}
		
		override public function initialize(poolObjectParams:Object=null):void {
			super.initialize(poolObjectParams);
		}
		
		public function containsPoint(pointX:Number, pointY:Number):Boolean {
			if (pointX >= x &&
				pointX < (x+width) &&
				pointY >= y &&
				pointY < (y+height)) {
				return true;
			} else return false;
		}
		
		public static function pixelToGrid(pixel:Number):Number {
			return Math.floor(pixel / Constants.STONE_WIDTH);
		}
		
		public function xToGrid():Number {
			return pixelToGrid(x);
		}
		
		public function yToGrid():Number {
			return pixelToGrid(y);
		}
		
		public function heroLeft(hero:Hero):void {}
		public function heroEntered(hero:Hero):void {}
		public function heroStayed(hero:Hero):void {};
	}
}
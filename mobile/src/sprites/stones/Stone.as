package sprites.stones
{
	import citrus.objects.CitrusSprite;
	
	public class Stone extends CitrusSprite
	{
		public function Stone(name:String, params:Object=null) {
			super(name, params);
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
	}
}
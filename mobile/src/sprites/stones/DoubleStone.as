package sprites.stones
{
	import starling.display.Image;

	public class DoubleStone extends Stone
	{
		public static var REMOVE_DELAY:Number = 1;
		
		protected var _enterCount:Number = 0;
		
		protected var _heroEntered:Boolean = false;
		protected var _timeSinceHeroEnter:Number = 0;
		
		
		public function DoubleStone(name:String, params:Object=null) {
			super(name, params);
			updateCallEnabled = true;
		}
		
		override public function update(timeDelta:Number):void {
			super.update(timeDelta);
			if (_heroEntered) {
				_timeSinceHeroEnter += timeDelta;
				if (_timeSinceHeroEnter > REMOVE_DELAY) {
					destroyed.dispatch(this);
					destroy();
				}
			}
		}
		
		override public function heroEntered(hero:Hero):void {
			super.heroEntered(hero);
			_heroEntered = true;
			_timeSinceHeroEnter = 0;
			_enterCount++;
			view = new Image((_ce as SensitiveRevival).assetsManager.getTexture("single_stone"));
		}
		
		override public function heroLeft(hero:Hero):void {
			super.heroLeft(hero);
			if (_enterCount == 1) {
				_heroEntered = false;
			}
		}
		
		override public function destroy():void {
			destroyed.removeAll();
			updateCallEnabled = false;
			super.destroy();
		}
	}
}
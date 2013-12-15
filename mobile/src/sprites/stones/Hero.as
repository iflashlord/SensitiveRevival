package sprites.stones
{
	import citrus.objects.CitrusSprite;
	
	import org.osflash.signals.Signal;

	public class Hero extends CitrusSprite
	{
		public const DIR_NONE:Number = 0;
		public const DIR_UP:Number = 1;
		public const DIR_RIGHT:Number = 2;
		public const DIR_DOWN:Number = 3;
		public const DIR_LEFT:Number = 4;
		
		public var stepDone:Signal;
		
		protected var _dir:Number = DIR_NONE;
		protected var _speed:Number = 80;
		protected var _stepInProgress:Boolean = false;
		protected var _targetX:Number = 0;
		protected var _targetY:Number = 0;
		
		public function Hero(name:String, params:Object=null)
		{
			super(name, params);
			updateCallEnabled = true;
			velocity = [0,0];
			stepDone = new Signal();
			
			view.pivotX = width >> 1;
			view.pivotY = height >> 1;
		}
		
		override public function initialize(poolObjectParams:Object=null):void {
			super.initialize(poolObjectParams);
		}
		
		override public function update(timeDelta:Number):void {
			super.update(timeDelta);
			if (_stepInProgress) {
				checkStepDone();
			}
						
		}
		
		protected function checkStepDone():void {
			switch (_dir) {
				case DIR_RIGHT:
					if (x >= _targetX) {
						stopMoving();
					}
					break;
				case DIR_LEFT:
					if (x <= _targetX) {
						stopMoving();
					}
					break;
				case DIR_UP:
					if (y <= _targetY) {
						stopMoving();
					}
					break;
				case DIR_DOWN:
					if (y >= _targetY) {
						stopMoving();
					}
					break;
			}
		}
		
		protected function stopMoving():void {
			velocity = [0,0];
			x = _targetX;
			y = _targetY;
			_dir = DIR_NONE;
			_targetX = 0;
			_targetY = 0;
			_stepInProgress = false;
			stepDone.dispatch();
		}
		
		public function goRight():void {
			if (!_stepInProgress) {
				_targetX = x + Constants.STONE_WIDTH;
				_targetY = y;
				_dir = DIR_RIGHT;
				velocity = [_speed,0];
				_stepInProgress = true;
			}
		}
		
		public function goLeft():void {
			if (!_stepInProgress) {
				_targetX = x - Constants.STONE_WIDTH;
				_targetY = y;
				_dir = DIR_LEFT;
				velocity = [-1 * _speed,0];
				_stepInProgress = true;
			}
		}
		
		public function goUp():void {
			if (!_stepInProgress) {
				_targetX = x;
				_targetY = y - Constants.STONE_WIDTH;
				_dir = DIR_UP;
				velocity = [0,-1 * _speed];
				_stepInProgress = true;
			}
		}
		
		public function goDown():void {
			if (!_stepInProgress) {
				_targetX = x;
				_targetY = y + Constants.STONE_WIDTH;
				_dir = DIR_DOWN;
				velocity = [0,_speed];
				_stepInProgress = true;
			}
		}
	}
}
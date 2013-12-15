/**
 * The Base class for all Flash Pro-generated and imported Levels.
 * 
 * Issues signals for Level End and Level Restart
 * 
 * (c) 2013-2014 Alexander Schenkel
 */
package levels {
import flash.display.MovieClip;

import citrus.core.CitrusEngine;
import citrus.core.CitrusObject;
import citrus.core.starling.StarlingState;
import citrus.input.InputController;
import citrus.input.controllers.Keyboard;
import citrus.objects.CitrusSprite;

import org.osflash.signals.Signal;

import sprites.stones.DoubleStone;
import sprites.stones.FixStone1x1;
import sprites.stones.Hero;
import sprites.stones.SingleStone;
import sprites.stones.Stone;

import starling.core.Starling;
import starling.utils.Color;

import utils.EnhancedObjectMakerStarling;
import utils.MobileAssetManager;


	// Note that you must create an abstract class : Level1 and Level2 extends ALevel 
	// which extends the State or StarlingState class. From our example :
	public class ALevel extends StarlingState {
		
		public var lvlEnded:Signal;
		public var restartLevel:Signal;
		protected var _level:MovieClip;
		//private var physics:Nape;
		
		protected var assetsManager:MobileAssetManager;
		private var _backgroundMusic:String;
		
		/*
		protected var timeDisplay: TimeDisplay;
		protected var pointsDisplay: PointsDisplay;
		*/
		private var lastStoneUpdate:Number = 0;
		
		//private var pausePopUp:PausePopUp;
		private var touchInputController:InputController;
		
		protected var _hero:Hero;
		
		/**
		 * An array containing references to all stones, on their correct
		 * grid position (indexes x,y).
		 */
		protected var _stones:Array;
		
		protected var _actStone:Stone = null;
		
		public function ALevel(level:MovieClip = null) {
			super();
			assetsManager = (_ce as SensitiveRevival).assetsManager;
			_level = level;
			objectsUsedInLevel();
			initSignals();
		}
		
		private function objectsUsedInLevel():void
		{
			//var objectsUsed:Array = [Hero, Ground, Swamp, Platform, EnemySmall, EnemyNormal, EnemyBig, EnemyFlying, EnemyJumping, GoodApple, FallingApple, BigApple, BadApple, Sensor, CitrusSprite, Box, MovingPlatform, OrangeSprite];
			var objectsUsed:Array = [CitrusSprite,Stone,FixStone1x1,SingleStone,Hero,DoubleStone];
		}
		
		private function initSignals():void
		{
			lvlEnded = new Signal();
			restartLevel = new Signal();
			CitrusEngine.getInstance().input.keyboard.onKeyDown.add(handleKeypress);
		}
		
		override public function initialize():void {
			super.initialize();
			stage.color = Color.BLACK;
			Starling.current.nativeStage.color = Color.BLACK;			

			buildLevel();
			
			//(_ce as SensitiveRevival).showMobileControls();
			
			createUIElements();
			
			assetsManager.stopAllSounds();
			assetsManager.play(backgroundMusic);
			
			CitrusEngine.getInstance().input.resetActions();
			
			addBackground();
			setUpCamera();
			initListeners();
			
			trace(stage.stageWidth,stage.stageHeight )
			
			
		}
		
		private function buildLevel():void
		{
			EnhancedObjectMakerStarling.FromMovieClip(_level, assetsManager, true);
			_hero = getObjectByName("hero") as Hero;
			_hero.stepDone.add(heroStepDone);
			
			var levelStones:Vector.<CitrusObject> = getObjectsByType(Stone); 
			_stones = new Array(Constants.STONES_X);
			for (var loopX:int = 0; loopX < Constants.STONES_X; loopX++) {
				_stones[loopX] = new Array(Constants.STONES_Y);
			}
			for each (var stone:Stone in levelStones) {
				stone.destroyed.add(stoneDestroyed);
				_stones[stone.xToGrid()][stone.yToGrid()] = stone;
			}
		}
		
		private function addBackground():void
		{
			/*
			var backImg:Image = new Image(assetsManager.getTexture(Constants.textures.STATIC));
			backImg.width = stage.stageWidth;
			backImg.height = stage.stageHeight;
			backImg.blendMode = BlendMode.NONE;
			addChildAt(backImg,0);
			*/
		}
		
		private function setUpCamera():void
		{
			//var hero:CitrusObject = getObjectByName(Constants.ObjectNames.HERO) as Hero;
		//	view.camera.setUp(this,new Rectangle(0,0,Constants.STAGE_WIDTH,Constants.STAGE_HEIGHT),new Point(0.5,1),new Point(.1,.1));
		}
		
		private function initListeners():void
		{
			/*
			var goalSensor:Goal = getObjectByName(Constants.ObjectNames.GOAL_SENSOR) as Goal;
			goalSensor.onHeroReachedGoal(finish);
			*/
		}
		
		private function createUIElements():void
		{
			//showTimeDisplay();
			//showPointsDisplay();
			//showPauseButton();
			//createPausePopUp();
		}
		
		/*
		private function showTimeDisplay():void
		{
			timeDisplay = new TimeDisplay();
			timeDisplay.pivotX = timeDisplay.width;
			timeDisplay.x = stage.stageWidth - 20;
			addChild(timeDisplay);
		}
		*/
		
		/*
		private function showPointsDisplay():void
		{
			pointsDisplay = new PointsDisplay();
			pointsDisplay.pivotX = pointsDisplay.width / 2;
			pointsDisplay.x = stage.stageWidth / 2;
			addChild(pointsDisplay);
		}
		*/
		
		/*
		private function showPauseButton():void
		{
			var pauseBtn:PauseBtn = new PauseBtn();
			pauseBtn.x = 5;
			pauseBtn.y = 5;
			pauseBtn.triggered.add(pause);
			addChild(pauseBtn);
		}
		*/
		
		/*
		private function createPausePopUp():void
		{
			pausePopUp = new PausePopUp();
			pausePopUp.onHome.add(home);
			pausePopUp.unpaused.add(unpause);
			pausePopUp.restarted.add(restart);
		}
		*/
		
		
		protected function restart():void {
			onUnPause();
			restartLevel.dispatch();
		}
		
		protected function finish():void
		{
			(_ce as SensitiveRevival).isFinish = true;
			(_ce as SensitiveRevival).playing = false;

			/*
			var gameEndedPopUp:GameEndedPopUp = new GameEndedPopUp();
			gameEndedPopUp.onRestart.add(restart);
			gameEndedPopUp.onHome.add(home);
			gameEndedPopUp.show(pointsDisplay.counter, timeDisplay.counter * 1000);
			*/
		}
		
		protected function pause():void {
			_ce.playing = false;
		}
		
		public function onPause():void
		{
			/*
			assetsManager.pauseSound(backgroundMusic);
			assetsManager.play(Constants.sounds.SFX_PAUSE_ON);
			if (pausePopUp) {
				pausePopUp.show();
			}
			*/
			
		}
		
		protected function unpause(): void {
			_ce.playing = true;
		}
		
		public function onUnPause():void
		{
			/*
			if (pausePopUp) {
				pausePopUp.hide();
				assetsManager.resumeSound(backgroundMusic);			
			}
			*/
		}
		
		override public function update(timeDelta:Number):void{
			super.update(timeDelta);
			lastStoneUpdate += timeDelta;
			var stone:Stone = _stones[Stone.pixelToGrid(_hero.x)][Stone.pixelToGrid(_hero.y)];
			
				if (stone != _actStone) {
					if (_actStone) _actStone.heroLeft(_hero);
					if (stone) stone.heroEntered(_hero);
					_actStone = stone;
				} else if (_actStone != null) {
					_actStone.heroStayed(_hero);
				}
		}
		
		
		public function home():void {
			//(_ce as SensitiveRevival).hideMobileControls();
			lvlEnded.dispatch();
		}
		
		public function doIdleAction():void{
			
		}

		protected function get backgroundMusic():String
		{
			//_backgroundMusic = Constants.sounds.GAME_MUSIC;
			return _backgroundMusic;
		}

		protected function set backgroundMusic(value:String):void
		{
			_backgroundMusic = value;
		}
		
		protected function handleKeypress(keyCode:uint,keyLocation:Number):void{
			switch (keyCode){
				case Keyboard.ENTER:	
					break;
				case Keyboard.UP:
					handleUpKey();			
					break;
				case Keyboard.RIGHT:
					handleRightKey();			
					break;
				case Keyboard.DOWN:
					handleDownKey();			
					break;
				case Keyboard.LEFT:
					handleLeftKey();			
					break;
			}
		}
		
		protected function handleUpKey():void {
			_hero.goUp();
		}
		
		protected function handleRightKey():void {
			_hero.goRight();
		}
		protected function handleDownKey():void {
			_hero.goDown();	
		}
		protected function handleLeftKey():void {
			_hero.goLeft();
		}
		
		protected function heroStepDone():void {
			checkSolidGround(_hero.x, _hero.y);
		}
		
		protected function checkSolidGround(x:Number,y:Number):Boolean {
			var stone:Stone = _stones[Stone.pixelToGrid(x)][Stone.pixelToGrid(y)];
			if (stone) return true;
			return false;
		}
		
		protected function stoneDestroyed(stone:Stone):void {
			trace("Stone destroyed: ",stone);
			remove(stone);
			if (stone.containsPoint(_hero.x,_hero.y)) {
				heroDies();
			}
			_stones[Stone.pixelToGrid(stone.x)][Stone.pixelToGrid(stone.y)] = null;
		}
		
		protected function heroDies():void {
			trace("Uh oh, die!");
			remove(_hero);
		}
	}
}
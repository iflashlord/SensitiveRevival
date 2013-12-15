package ui
{
	import citrus.core.CitrusEngine;
	
	import feathers.controls.Button;
	
	import org.osflash.signals.Signal;
	
	import starling.display.Image;
	import starling.events.Event;
	
	public class AIconButton extends Button
	{
		public var triggered:Signal;
		public function AIconButton(iconTextureId:String, width:Number = 0, height:Number = 0, downTextureId:String = null) {
			super();
			addEventListener( Event.TRIGGERED, onTrigger );
			var icon:Image = new Image((CitrusEngine.getInstance() as SensitiveRevival).assetsManager.getTexture(iconTextureId));
			
			this.height = height || icon.height;
			this.width = width || icon.width;
			this.defaultSkin = icon;
			
			if (downTextureId) {
				var downIcon:Image = new Image((CitrusEngine.getInstance() as SensitiveRevival).assetsManager.getTexture(downTextureId));
				this.downSkin = downIcon;
			}
			triggered = new Signal();
		}
		
		protected function onTrigger():void  {
			triggered.dispatch();
		}
	}
}
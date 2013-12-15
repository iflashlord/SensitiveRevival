package
{
	public class Constants
	{
		public static var DEBUG:Boolean = true;
		public static var PHYSICS:Boolean = true;
		
		public static var STAGE_WIDTH:int = 480;
		public static var STAGE_HEIGHT:int = 320;
		public static var STONE_WIDTH:int = 20;
		
		//fonts
		[Embed(source="/../assets/fonts/unispace-rg.ttf",fontName="unispace",mimeType="application/x-font",embedAsCFF="false")]
		private static const _font_score:Class;
		
		[Embed(source="/../assets/fonts/unispace-bitmap/font.fnt", mimeType="application/octet-stream")]
		public static const UnispaceBitmapXml:Class;
		
		[Embed(source="/../assets/fonts/unispace-bitmap/font.png")]
		public static const UnispaceBitmapTexture:Class;
		
		public static const textures:Object = {
			PLAY_BTN: "play_btn",
			PLAY_DOWN: "play_btn_down"
		};
	}
}
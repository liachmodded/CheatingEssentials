package com.luna.ce.gui.widget.skin;

import com.luna.ce.gui.GuiUtils;
import com.luna.ce.gui.widget.base.ISkin;

public class SkinCE implements ISkin {
	@Override
	public void drawComponent( final double x, final double y, final double width, final double height,
			final boolean isOver ) {
		GuiUtils.drawGradientBorderedRect( ( int ) x, ( int ) y, ( int ) width, ( int ) height, 1.0F,
				0xffffffff, 0xff777777, isOver ? 0xff008888 : 0xff888888 );
	}
	
	@Override
	public void drawWindow( final double x, final double y, final double width, final double height,
			final boolean isOver ) {
		GuiUtils.drawGradientRect( x, y, width, height, 0xFF111111, 0xFF222222 );
		GuiUtils.drawBorderedRect( x + 0.5, y, width - 0.5, height - 0.5, 1.0F, 0xFF000000, 0x0 );
	}
	
	@Override
	public void drawControls( final double x, final double y, final double width, final double height,
			final boolean isOver ) {
		GuiUtils.drawBorderedRect( x, y, width, height, 1.0F, 0xFF000000, isOver ? 0xFF888888 : 0xFF666666 );
	}
	
	@Override
	public void drawButton( final double x, final double y, final double width, final double height,
			final boolean isOver ) {
		GuiUtils.drawGradientBorderedRect( x, y, width, height, 1.0F, 0xFF000000, isOver ? 0xFFB42715
				: 0xFF666666, isOver ? 0xFFD64937 : 0xFF444444 );
	}
	
	@Override
	public void drawLabel( final double x, final double y, final double width, final double height,
			final boolean isOver ) {
		// TODO If you want Labels to have an outline or something
	}
	
	@Override
	public void drawRadioButton( final double x, final double y, final double width, final double height,
			final boolean isActive ) {
		GuiUtils.drawRect( ( 2 ), ( 2 ), ( 12 ), ( 12 ), isActive ? 0x77000000 : 0x0 );
	}
	
	@Override
	public void drawSlider( final int x, final int y, final int width, final int height, final boolean b ) {
		if( b ) {
			GuiUtils.drawGradientBorderedRect( x, y, width, height, 1.0F, 0xFF000000, 0xFF333333, 0xFF555555 );
		} else if( !b ) {
			GuiUtils.drawGradientBorderedRect( x, y, width, height, 1.0F, 0xFF000000, 0xFF202020, 0xFF070707 );
		}
	}
	
	@Override
	public int getTextColor( final boolean window ) {
		// TODO Auto-generated method stub
		return 0xffffff;
	}
}

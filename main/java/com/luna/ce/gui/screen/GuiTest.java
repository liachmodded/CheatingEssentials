package com.luna.ce.gui.screen;

import com.luna.ce.config.Config;
import com.luna.ce.gui.a.AbstractComponent;
import com.luna.ce.gui.a.AbstractContainer;
import com.luna.ce.gui.action.defaults.ClickSoundAction;
import com.luna.ce.gui.action.i.MouseDownAction;
import com.luna.ce.gui.action.i.TickAction;
import com.luna.ce.gui.components.BasicFrame;
import com.luna.ce.gui.components.BasicSlider;
import com.luna.ce.gui.components.BasicWidget;
import com.luna.ce.gui.screen.base.ContainerGuiScreen;
import com.luna.ce.gui.skin.a.AbstractSkin;
import com.luna.ce.gui.skin.manager.SkinManager;
import com.luna.ce.manager.ManagerModule;
import com.luna.ce.module.EnumModuleType;
import com.luna.ce.module.Module;
import com.luna.lib.datastructures.Value;
import com.luna.lib.exception.InitializationException;

@SuppressWarnings({"unused", "unchecked"})
public class GuiTest extends ContainerGuiScreen {
    @Override
    public void initialize() throws InitializationException {
        for(EnumModuleType t : EnumModuleType.values()) {
            AbstractContainer moduleContainer = new BasicFrame(t.getRealName());
            for(final Module m : ManagerModule.getInstance().getModules()) {
                if(m.getType().equals(t)) {
                    final BasicWidget btn = new BasicWidget(m.getName(), "button");
                    btn.addAction(new ClickSoundAction());
                    btn.addAction(new TickAction() {
                        @Override
                        public void onTick(AbstractComponent component) {
                            btn.setFocused(m.getActive());
                        }
                    });
                    btn.addAction(new MouseDownAction() {
                        @Override
                        public void onClick(AbstractComponent component, int button) {
                            if (button == 0) {
                                m.toggle();
                            }
                        }
                    });
                    moduleContainer.addChild(btn);
                }
            }
            if (moduleContainer.getChildren().size() > 1) {
                addThing(moduleContainer);
            }
        }

        AbstractContainer skins = new BasicFrame("Skins");
        for (final AbstractSkin skin : SkinManager.getInstance().getAvailableSkins()) {
            BasicWidget btn = new BasicWidget(skin.getName(), "button");
            btn.addAction(new MouseDownAction() {
                        @Override
                        public void onClick(AbstractComponent component, int button) {
                            SkinManager.getInstance().setCurrentSkin(skin);
                            component.getParent().unfocusAllChildrenExcept(component);
                        }
                    }
            );
            if (skin.getName().equals("Numixish")) {
                btn.focus();
            }
            skins.addChild(btn);
        }
        addThing(skins);

        Value value = new Value("Test", 4, 1, 7);
        Value value2 = new Value("Other Test", 4, 1, 7);

        AbstractContainer containerTest = new BasicFrame("Scroll Test"); // WidgetFactory.getInstance().construct("frame", "Scroll test");
        for (int i = 0; i < 15; i++) {
            containerTest.addChild(new BasicWidget("Button " + i, "button")); // WidgetFactory.getInstance().construct("label", "Label " + i));
        }
        for (int i = 0; i < 15; i++) {
            containerTest.addChild(new BasicWidget("Label " + i, "label")); // WidgetFactory.getInstance().construct("label", "Label " + i));
        }
        containerTest.addChild(new BasicSlider(value));
        containerTest.addChild(new BasicSlider(value2));
        addThing(containerTest);
    }

    @Override
    public void onGuiClosed() {
        Config.getInstance().saveGuiConfig();
    }

    public void loadGuiConfig() {
        Config.getInstance().loadGuiConfig();
    }
}


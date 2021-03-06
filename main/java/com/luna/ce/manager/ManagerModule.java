package com.luna.ce.manager;

import java.util.LinkedList;
import java.util.List;

import com.luna.ce.log.CELogger;
import com.luna.ce.module.Module;
import com.luna.ce.module.classes.ModuleAdvancedTooltips;
import com.luna.ce.module.classes.ModuleAnimalESP;
import com.luna.ce.module.classes.ModuleAntiArrow;
import com.luna.ce.module.classes.ModuleArrayList;
import com.luna.ce.module.classes.ModuleAutoRespawn;
import com.luna.ce.module.classes.ModuleBlockOverlay;
import com.luna.ce.module.classes.ModuleBreadcrumbs;
import com.luna.ce.module.classes.ModuleChestESP;
import com.luna.ce.module.classes.ModuleDolphin;
import com.luna.ce.module.classes.ModuleFastBreak;
import com.luna.ce.module.classes.ModuleFastplace;
import com.luna.ce.module.classes.ModuleFly;
import com.luna.ce.module.classes.ModuleFullbright;
import com.luna.ce.module.classes.ModuleGui;
import com.luna.ce.module.classes.ModuleHighJump;
import com.luna.ce.module.classes.ModuleJumpStep;
import com.luna.ce.module.classes.ModuleMobESP;
import com.luna.ce.module.classes.ModuleNoFall;
import com.luna.ce.module.classes.ModuleNoWeather;
import com.luna.ce.module.classes.ModuleNoWeb;
import com.luna.ce.module.classes.ModulePlayerESP;
import com.luna.ce.module.classes.ModuleReloadChunks;
import com.luna.ce.module.classes.ModuleSneak;
import com.luna.ce.module.classes.ModuleSprint;
import com.luna.ce.module.classes.ModuleStep;
import com.luna.ce.module.classes.ModuleTest;
import com.luna.lib.annotations.reflection.loading.Experimental;
import com.luna.lib.annotations.reflection.loading.Loadable;
import com.luna.lib.annotations.reflection.loading.TestClass;
import com.luna.lib.loggers.enums.EnumLogType;

public class ManagerModule {
    private static ManagerModule instance;
    // private static final Class< ? > classToLoadFrom = ModuleTest.class;

    private final List<Module> modules;

    public ManagerModule() {
        // CELogger.getInstance( ).log( EnumLogType.SCAN,
        // "Searching for and loading modules..." );
        modules = new LinkedList<Module>( /*
                                             * Arrays.asList(
											 * getModulesFromPackage(
											 * classToLoadFrom ) )
											 */);
        addModulesByHand();
        CELogger.getInstance().log(EnumLogType.DEBUG,
                String.format("Modules loaded: %s!", modules.size()));
    }

    public static ManagerModule getInstance() {
        if (instance == null) {
            instance = new ManagerModule();
        }
        return instance;
    }

    private void addModulesByHand() {
        CELogger.getInstance().log(EnumLogType.WARNING,
                "Well, since Forge doesn't like my Reflection for loading modules, gotta do it by hand :(");
        // @formatter:off
        addModules(
                new ModuleTest(),
                new ModuleChestESP(),
                new ModuleAnimalESP(),
                new ModuleMobESP(),
                new ModuleAntiArrow(),
                new ModuleAutoRespawn(),
                new ModuleAdvancedTooltips(),
                new ModuleSprint(),
                new ModuleSneak(),
                new ModuleJumpStep(),
                new ModuleDolphin(),
                new ModuleHighJump(),
                new ModuleNoWeather(),
                new ModuleNoWeb(),
                new ModuleReloadChunks(),
                new ModuleFullbright(),
                new ModuleNoFall(),
                new ModuleFastBreak(),
                new ModuleBreadcrumbs(),
                new ModuleStep(),
                new ModuleArrayList(),
                new ModuleBlockOverlay(),
                new ModuleGui(),
                new ModuleFly(),
                new ModuleFastplace(),
                new ModulePlayerESP()
        );
        // @formatter:on
    }

    private void addModules(final Module... modules) {
        synchronized (this.modules) {
            for (final Module e : modules) {
                if (!e.getClass().isAnnotationPresent(Loadable.class)) {
                    CELogger.getInstance().log(
                            EnumLogType.WARNING,
                            String.format("Module %s is not marked as loadable, skipping...", e.getClass()
                                    .getSimpleName()));
                    continue;
                }
                if (e.getClass().isAnnotationPresent(Experimental.class)) {
                    CELogger.getInstance().log(
                            EnumLogType.WARNING,
                            String.format("Module %s is marked as experimental, use at own risk!", e
                                    .getClass().getSimpleName()));
                }
                if (e.getClass().isAnnotationPresent(TestClass.class)) {
                    CELogger.getInstance().log(
                            EnumLogType.WARNING,
                            String.format("Module %s is marked as a test class, use at own risk!", e
                                    .getClass().getSimpleName()));
                }
                this.modules.add(e);
            }
        }
    }

    /**
     * Parses the class code source to get modules from the retrieved classes
     *
     * @param classe class to get code source for
     * @return module array from package
     */
    // @formatter:off
	/*private Module[ ] getModulesFromPackage( final Class< ? > classe ) {
		final List< Module > modules = new ArrayList<>();
		final Class< ? >[ ] classes = ClassEnumerator.getInstance().getClassesFromPackage( classe );
		for ( final Class< ? > c : classes ) {
			if ( Module.class.isAssignableFrom( c ) && !c.equals( Module.class ) ) {
				if ( !c.isAnnotationPresent( Loadable.class ) ) {
					CELogger.getInstance().log( EnumLogType.WARNING,
							String.format( "Skipping %s because it is not marked as loadable", c.getName() ) );
					continue;
				}
				CELogger.getInstance().log( EnumLogType.REFLECTION,
						String.format( "Instantiating %s...", c.getName() ) );

				if ( c.isAnnotationPresent( Experimental.class ) ) {
					CELogger.getInstance()
							.log( EnumLogType.WARNING,
									String.format( "Module of class %s is marked as experimental, use with (lots of) caution!" ) );
				}

				if ( c.isAnnotationPresent( TestClass.class ) ) {
					CELogger.getInstance().log(
							EnumLogType.WARNING,
							String.format( "Module of class %s is marked as a test class, "
									+ "it would be preferable that you not use it!" ) );
				}

				try {
					modules.add( ( Module ) c.newInstance() );
				} catch ( final InstantiationException e ) {
					e.printStackTrace();
				} catch ( final IllegalAccessException e ) {
					e.printStackTrace();
				}
			}
		}
		return modules.toArray( new Module[ modules.size() ] );
	}*/
    // @formatter:on
    @SuppressWarnings("unchecked")
    public <T extends Module> T getModuleByClass(final Class<T> c) {
        synchronized (modules) {
            for (final Module e : modules) {
                if (e.getClass().equals(c)) {
                    return (T) e;
                }
            }
        }
        CELogger.getInstance().log(EnumLogType.WARNING,
                String.format("Module %s not found by class, returning null!", c.getCanonicalName()));
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> T getModuleByName(final String name) {
        synchronized (modules) {
            for (final Module e : modules) {
                if (e.getName().replaceAll(" ", "").toLowerCase()
                        .equals(name.toLowerCase().replaceAll(" ", ""))) {
                    return (T) e;
                }
            }
        }
        CELogger.getInstance().log(EnumLogType.WARNING,
                String.format("Module %s not found by class, returning null!", name));
        return null;
    }

    public List<Module> getModules() {
        synchronized (modules) {
            return modules;
        }
    }
}

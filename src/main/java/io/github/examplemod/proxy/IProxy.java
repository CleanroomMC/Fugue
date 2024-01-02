package io.github.examplemod.proxy;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public interface IProxy {

	void preInit(FMLPreInitializationEvent event);

	void init(FMLInitializationEvent event);

	void postInit(FMLPostInitializationEvent event);

	/**
	 * Get the client player.
	 *
	 * @return The client player
	 * @throws WrongSideException If called on the dedicated server.
	 */
	@Nullable
	EntityPlayer getClientPlayer();

	/**
	 * Get the client {@link World}.
	 *
	 * @return The client World
	 * @throws WrongSideException If called on the dedicated server.
	 */
	@Nullable
	World getClientWorld();

	/**
	 * Get the {@link IThreadListener} for the {@link MessageContext}'s
	 * {@link Side}.
	 *
	 * @param context The message context
	 * @return The thread listener
	 */
	IThreadListener getThreadListener(MessageContext context);

	/**
	 * Get the {@link EntityPlayer} from the {@link MessageContext}.
	 *
	 * @param context The message context
	 * @return The player
	 */
	EntityPlayer getPlayer(MessageContext context);

	/**
	 * Thrown when a proxy method is called from the wrong side.
	 */
	class WrongSideException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public WrongSideException(final String message) {
			super(message);
		}

		public WrongSideException(final String message, final Throwable cause) {
			super(message, cause);
		}
	}
}
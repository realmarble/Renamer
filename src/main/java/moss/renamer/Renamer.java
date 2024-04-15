package moss.renamer;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import static net.minecraft.server.command.CommandManager.*;
import me.lucko.fabric.api.permissions.v0.Permissions;



public class Renamer implements ModInitializer {
	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("rename")
				.requires(Permissions.require("moss.renamer.rename"))
				.then(argument("name", StringArgumentType.string())
				.executes(RenameCommand::setName)
				)));
	}
}
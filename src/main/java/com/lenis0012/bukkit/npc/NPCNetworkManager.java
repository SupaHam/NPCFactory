package com.lenis0012.bukkit.npc;

import net.minecraft.server.v1_7_R4.NetworkManager;
import net.minecraft.util.io.netty.channel.Channel;
import net.minecraft.util.io.netty.util.AttributeKey;

import java.lang.reflect.Field;
import java.net.SocketAddress;

public class NPCNetworkManager extends NetworkManager {

	public NPCNetworkManager() {
		super(false);
		
		try {
			Field channel = NetworkManager.class.getDeclaredField("m");
			Field address = NetworkManager.class.getDeclaredField("n");
			
			channel.setAccessible(true);
			address.setAccessible(true);
			
			Channel parent = new NPCChannel(null);
                        try {
                          Field protocolVersion = NetworkManager.class.getDeclaredField("protocolVersion");
                          parent.attr(((AttributeKey<Integer>) protocolVersion.get(null))).set(5);
                        } catch(NoSuchFieldException ignored) { // This server isn't spigot, we're good.
                        }
			channel.set(this, parent);
			address.set(this, new SocketAddress() {
				private static final long serialVersionUID = 6994835504305404545L;
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}

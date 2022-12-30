package com.logouttimer;

import com.google.inject.Provides;

import java.time.Duration;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import net.runelite.api.Client;

import net.runelite.api.events.HitsplatApplied;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.timers.GameTimer;
import static net.runelite.client.plugins.timers.GameTimer.PICKPOCKET_STUN;
import net.runelite.client.plugins.timers.TimerTimer;


@Slf4j
@PluginDescriptor(
	name = "Example"
)
public class LogOutTimerPlugin extends Plugin
{

	@Inject
	private Client client;

	@Inject
	private LogOutTimerConfig config;

	@Override
	protected void startUp() throws Exception
	{
		//TODO: overlay
	}

	@Override
	protected void shutDown() throws Exception
	{

	}
	private void removeGameTimer(net.runelite.client.plugins.timers.GameTimer timer)
	{
		infoBoxManager.removeIf(t -> t instanceof TimerTimer && ((TimerTimer) t).getTimer() == timer);
	}
	private TimerTimer createGameTimer(final net.runelite.client.plugins.timers.GameTimer timer, Duration duration)
	{
		removeGameTimer(timer);

		TimerTimer t = new TimerTimer(timer, duration, this);
		switch (timer.getImageType())
		{
			case SPRITE:
				spriteManager.getSpriteAsync(timer.getImageId(), 0, t);
				break;
			case ITEM:
				t.setImage(itemManager.getImage(timer.getImageId()));
				break;
		}
		t.setTooltip(timer.getDescription());
		infoBoxManager.addInfoBox(t);
		return t;
	}

	@Subscribe
	public void onHitsplatApplied(HitsplatApplied event)
	{
		if (event.getActor() != client.getLocalPlayer())
		{
			return;
		}
		createGameTimer(PICKPOCKET_STUN, Duration.ofSeconds(6));

	}

	@Provides
	LogOutTimerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(LogOutTimerConfig.class);
	}
}

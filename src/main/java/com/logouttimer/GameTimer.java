package com.logouttimer;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Getter;
import net.runelite.api.SpriteID;

@Getter(AccessLevel.PACKAGE)
public enum GameTimer
{

	COMBAT(SpriteID.COMBAT_SPECIAL_ATTACK_BUTTON, GameTimerImageType.SPRITE, "Combat Logout Timer", true);

	@Nullable
	private final Duration duration;
	@Nullable
	private final Integer graphicId;
	private final String description;
	private final boolean removedOnDeath;
	private final int imageId;
	private final GameTimerImageType imageType;

	GameTimer(int imageId, GameTimerImageType idType, String description, Integer graphicId, long time, TemporalUnit unit, boolean removedOnDeath)
	{
		this.description = description;
		this.graphicId = graphicId;
		this.duration = Duration.of(time, unit);
		this.imageId = imageId;
		this.imageType = idType;
		this.removedOnDeath = removedOnDeath;
	}

	GameTimer(int imageId, GameTimerImageType idType, String description, long time, TemporalUnit unit, boolean removeOnDeath)
	{
		this(imageId, idType, description, null, time, unit, removeOnDeath);
	}

	GameTimer(int imageId, GameTimerImageType idType, String description, long time, TemporalUnit unit)
	{
		this(imageId, idType, description, null, time, unit, false);
	}

	GameTimer(int imageId, GameTimerImageType idType, String description, boolean removedOnDeath)
	{
		this.duration = null;
		this.graphicId = null;
		this.description = description;
		this.removedOnDeath = removedOnDeath;
		this.imageId = imageId;
		this.imageType = idType;
	}
}

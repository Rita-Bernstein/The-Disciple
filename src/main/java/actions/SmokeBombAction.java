package chronomuncher.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import chronomuncher.ChronoMod;

public class SmokeBombAction extends AbstractGameAction {

	private AbstractPlayer p;
	private boolean upgraded = false;

	public SmokeBombAction(boolean upgraded) {
		this.upgraded = upgraded;
	}

	public void update() {

	    boolean fightingBoss = false;
	    for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
	      if (m.type == AbstractMonster.EnemyType.BOSS)
	      {
     	 	AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, "There's no escape!", true));
	        this.isDone = true;
	        return;
	      }
	    }

	    target = AbstractDungeon.player;
	    if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
	    {
	      AbstractDungeon.getCurrRoom().smoked = true;
	      AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmokeBombEffect(target.hb.cX, target.hb.cY)));
	      target.hideHealthBar();
	      target.isEscaping = true;
	      target.escapeTimer = 2.5F;
	    }	      

	    // if (this.upgraded) { AbstractDungeon.getCurrRoom().addGoldToRewards(20); }

   		this.isDone = true;	

	}
}
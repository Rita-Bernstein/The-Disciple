package chronomuncher.cards;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.DrawReductionPower;

import chronomuncher.cards.MetricsCard;
import chronomuncher.ChronoMod;
import chronomuncher.patches.Enum;
import chronomuncher.powers.DelayedLoseStrengthPower;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;

public class HandsUpG extends MetricsCard {
	public static final String ID = "HandsUpG";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;

	private static final int COST = 1;
	private static final int ATTACK_DMG = 14;
	private static final int UPGRADE_ATTACK_DMG = 5;
	private static final int STRDOWN = 1;
	private static final int STRDOWN_UPGRADE = 1;

	public HandsUpG() {
		super(ID, NAME, "chrono_images/cards/HandsUp.png", COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				Enum.CHRONO_GOLD, AbstractCard.CardRarity.COMMON,
				AbstractCard.CardTarget.ENEMY);

		this.baseDamage = ATTACK_DMG;
		this.baseMagicNumber = STRDOWN;
		this.magicNumber = this.baseMagicNumber;

		ReflectionHacks.setPrivate(this, AbstractCard.class, "tintColor", Color.YELLOW.cpy());
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
			new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));

		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new DrawReductionPower(p, this.magicNumber), this.magicNumber));
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeDamage(UPGRADE_ATTACK_DMG);
			// upgradeMagicNumber(STRDOWN_UPGRADE);
		}
	}
}
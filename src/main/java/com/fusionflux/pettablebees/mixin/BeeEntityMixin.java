package com.fusionflux.pettablebees.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BeeEntity.class)
public abstract class BeeEntityMixin extends AnimalEntity {

    @Shadow public abstract void setAngerTime(int ticks);

    protected BeeEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }


    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.AIR)) {
            this.world.playSound(player, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_BEE_POLLINATE, this.getSoundCategory(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
            if (!this.world.isClient) {
                this.setAngerTime(0);
            }else if(this.world.isClient){
                for(int i = 0; i < 3; ++i) {
                    double d = this.random.nextGaussian() * 0.02D;
                    double e = this.random.nextGaussian() * 0.02D;
                    double f = this.random.nextGaussian() * 0.02D;
                    this.world.addParticle(ParticleTypes.HEART, this.getParticleX(1.0D), this.getRandomBodyY() + 0.5D, this.getParticleZ(1.0D), d, e, f);
                }
            }

            return ActionResult.success(this.world.isClient);
        } else {
            return super.interactMob(player, hand);
        }
    }


}

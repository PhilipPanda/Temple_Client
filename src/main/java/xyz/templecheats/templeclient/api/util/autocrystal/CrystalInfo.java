package xyz.templecheats.templeclient.api.util.autocrystal;

import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.math.BlockPos;

public class CrystalInfo {
    public final float damage;
    public final PlayerInfo target;
    
    private CrystalInfo(float damage, PlayerInfo target) {
        this.damage = damage;
        this.target = target;
        
    }
    
    public static class BreakInfo extends CrystalInfo {
        public final EntityEnderCrystal crystal;
        
        public BreakInfo(float damage, PlayerInfo target, EntityEnderCrystal crystal) {
            super(damage, target);
            this.crystal = crystal;
        }
    }
    
    public static class PlaceInfo extends CrystalInfo {
        public final BlockPos pos;
        
        public PlaceInfo(float damage, PlayerInfo target, BlockPos pos) {
            super(damage, target);
            this.pos = pos;
        }
    }
}

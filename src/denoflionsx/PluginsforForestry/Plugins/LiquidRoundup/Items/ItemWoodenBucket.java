package denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.Items;

import denoflionsx.PluginsforForestry.Core.PfF;
import denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.client.IconConstants;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class ItemWoodenBucket extends ItemContainerBase {

    public static Class forgeLiquid;

    public ItemWoodenBucket(int itemID) {
        super(itemID, 1000, "item.pff.woodenbucket.name", "woodenBucket", IconConstants.woodenBucket);
        this.setIsBucket(true);
    }

    @Override
    public final void setIsBucket(boolean isBucket) {
        super.setIsBucket(isBucket);
        PfF.Proxy.print("Bucket flag changed for wooden bucket.");
    }

    static {
        try {
            if (Class.forName("net.minecraftforge.fluids.BlockFluidClassic") != null) {
                forgeLiquid = Class.forName("net.minecraftforge.fluids.BlockFluidClassic");
                PfF.Proxy.print("Forge Liquid Block implementation detected.");
            }
        } catch (Throwable s) {
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if (par1ItemStack.getItemDamage() == 0) {
            MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, true);
            if (movingobjectposition == null) {
                return par1ItemStack;
            } else {
                FillBucketEvent event = new FillBucketEvent(par3EntityPlayer, par1ItemStack, par2World, movingobjectposition);
                int id = par2World.getBlockId(event.target.blockX, event.target.blockY, event.target.blockZ);
                event.result = this.filledMap.get(id);
                if (event.result != null) {
                    event.result = event.result.copy();
                    event.setResult(Event.Result.ALLOW);
                } else {
                    event.setResult(Event.Result.DENY);
                }
                if (event.getResult() == Event.Result.ALLOW && event.result != null) {
                    par2World.setBlock(event.target.blockX, event.target.blockY, event.target.blockZ, 0);
                    if (par3EntityPlayer.capabilities.isCreativeMode) {
                        return par1ItemStack;
                    }
                    if (--par1ItemStack.stackSize <= 0) {
                        return event.result;
                    }
                    if (!par3EntityPlayer.inventory.addItemStackToInventory(event.result)) {
                        par3EntityPlayer.dropPlayerItem(event.result);
                    }
                    return par1ItemStack;
                }
            }
            return par1ItemStack;
        } else {
            double d0 = par3EntityPlayer.prevPosX + (par3EntityPlayer.posX - par3EntityPlayer.prevPosX) * (double) 1.0F;
            double d1 = par3EntityPlayer.prevPosY + (par3EntityPlayer.posY - par3EntityPlayer.prevPosY) * (double) 1.0F + 1.62D - (double) par3EntityPlayer.yOffset;
            double d2 = par3EntityPlayer.prevPosZ + (par3EntityPlayer.posZ - par3EntityPlayer.prevPosZ) * (double) 1.0F;
            MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, true);
            if (movingobjectposition == null) {
                return par1ItemStack;
            }
            int i = movingobjectposition.blockX;
            int j = movingobjectposition.blockY;
            int k = movingobjectposition.blockZ;
            if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE) {
                if (movingobjectposition.sideHit == 0) {
                    --j;
                }
                if (movingobjectposition.sideHit == 1) {
                    ++j;
                }
                if (movingobjectposition.sideHit == 2) {
                    --k;
                }
                if (movingobjectposition.sideHit == 3) {
                    ++k;
                }
                if (movingobjectposition.sideHit == 4) {
                    --i;
                }
                if (movingobjectposition.sideHit == 5) {
                    ++i;
                }
                if (!par3EntityPlayer.canPlayerEdit(i, j, k, movingobjectposition.sideHit, par1ItemStack)) {
                    return par1ItemStack;
                }
                if (this.tryPlaceContainedLiquid(par2World, d0, d1, d2, i, j, k, par1ItemStack) && !par3EntityPlayer.capabilities.isCreativeMode) {
                    return new ItemStack(par1ItemStack.itemID, 1, 0);
                }
            }
            return par1ItemStack;
        }
    }

    public boolean tryPlaceContainedLiquid(World world, double xOffset, double yOffset, double zOffset, int x, int y, int z, ItemStack item) {
        FluidStack f = FluidContainerRegistry.getFluidForFilledItem(item);
        if (f.getFluid().canBePlacedInWorld()) {
            int _liquidId = f.getFluid().getBlockID();
            if (_liquidId > 4096) {
                return false;
            }
            if (_liquidId <= 0) {
                return false;
            } else if (!world.isAirBlock(x, y, z) && world.getBlockMaterial(x, y, z).isSolid()) {
                return false;
            } else {
                // 7, 3
                if (Block.blocksList[_liquidId] instanceof BlockFluid) {
                    world.setBlock(x, y, z, _liquidId, 0, 3);
                } else {
                    if (forgeLiquid != null) {
                        if (forgeLiquid.isInstance(Block.blocksList[_liquidId])) {
                            world.setBlock(x, y, z, _liquidId, 0, 3);
                        } else {
                            world.setBlock(x, y, z, _liquidId, 7, 3);
                        }
                    }
                }
                world.notifyBlockOfNeighborChange(x, y, z, _liquidId);
                world.notifyBlockOfNeighborChange(x - 1, y, z, _liquidId);
                world.notifyBlockOfNeighborChange(x + 1, y, z, _liquidId);
                world.notifyBlockOfNeighborChange(x, y - 1, z, _liquidId);
                world.notifyBlockOfNeighborChange(x, y + 1, z, _liquidId);
                world.notifyBlockOfNeighborChange(x, y, z - 1, _liquidId);
                world.notifyBlockOfNeighborChange(x, y, z + 1, _liquidId);
                return true;
            }
        }
        return false;
    }
}

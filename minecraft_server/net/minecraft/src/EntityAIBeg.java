package net.minecraft.src;

public class EntityAIBeg extends EntityAIBase
{
    private EntityWolf theWolf;
    private EntityPlayer field_48145_b;
    private World worldObject;
    private float minPlayerDistance;
    private int field_48144_e;

    public EntityAIBeg(EntityWolf par1EntityWolf, float par2)
    {
        this.theWolf = par1EntityWolf;
        this.worldObject = par1EntityWolf.worldObj;
        this.minPlayerDistance = par2;
        this.setMutexBits(2);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        this.field_48145_b = this.worldObject.getClosestPlayerToEntity(this.theWolf, (double)this.minPlayerDistance);
        return this.field_48145_b == null ? false : this.func_48142_a(this.field_48145_b);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !this.field_48145_b.isEntityAlive() ? false : (this.theWolf.getDistanceSqToEntity(this.field_48145_b) > (double)(this.minPlayerDistance * this.minPlayerDistance) ? false : this.field_48144_e > 0 && this.func_48142_a(this.field_48145_b));
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.theWolf.func_48378_e(true);
        this.field_48144_e = 40 + this.theWolf.getRNG().nextInt(40);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.theWolf.func_48378_e(false);
        this.field_48145_b = null;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        this.theWolf.getLookHelper().setLookPosition(this.field_48145_b.posX, this.field_48145_b.posY + (double)this.field_48145_b.getEyeHeight(), this.field_48145_b.posZ, 10.0F, (float)this.theWolf.getVerticalFaceSpeed());
        --this.field_48144_e;
    }

    private boolean func_48142_a(EntityPlayer par1EntityPlayer)
    {
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        return var2 == null ? false : (!this.theWolf.isTamed() && var2.itemID == Item.bone.shiftedIndex ? true : this.theWolf.isWheat(var2));
    }
}

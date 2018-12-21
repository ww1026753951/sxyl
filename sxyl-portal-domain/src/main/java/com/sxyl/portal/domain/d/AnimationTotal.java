package com.sxyl.portal.domain.d;

import com.sxyl.portal.domain.constant.AnimationEnum;

import java.util.ArrayList;
import java.util.List;

/***
 *
 * 动画步骤总的方法
 */
public class AnimationTotal extends AnimationComponent {



    /***
     * 总计数器totalCount
     */
    private Integer tc;



    /****
     *分组内图画的对象
     */
    private List<AnimationComponent> acs;



    public Integer getTc() {
        return tc;
    }

    public void setTc(Integer tc) {
        this.tc = tc;
    }

    public List<AnimationComponent> getAcs() {
        return acs;
    }

    public void setAcs(List<AnimationComponent> acs) {
        this.acs = acs;
    }

    public AnimationTotal addComponent(AnimationComponent animationComponent) {
        if(this.acs==null){
            this.acs = new ArrayList<AnimationComponent>();
        }
        this.acs.add(animationComponent);
        return this;
    }



    @Override
    public void setAnimationType() {
        super.setAt(AnimationEnum.TOTAL.getType());
    }
}

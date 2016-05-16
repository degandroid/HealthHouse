package com.enjoyor.healthhouse.adapter;

import android.content.Context;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.bean.PhsicallLocation;

import java.util.List;

/**
 * Created by YuanYuan on 2016/5/13.
 */
public class PhysicallAdapter extends CommAdapter<PhsicallLocation> {

    public PhysicallAdapter(Context context, List<PhsicallLocation> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, PhsicallLocation phsicallLocation) {
//        holder.setImageURL()
        for (int i = 0; i < phsicallLocation.toString().length(); i++) {
            holder.setText(R.id.physicall_addr, phsicallLocation.getPhyicallLocation_infoList().get(i).getMachineAddress());
            holder.setText(R.id.physicall_kilo, String.valueOf(phsicallLocation.getPhyicallLocation_infoList().get(i).getDistance()));
        }
    }
}

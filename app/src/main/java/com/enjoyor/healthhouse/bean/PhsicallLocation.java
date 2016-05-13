package com.enjoyor.healthhouse.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

/**
 * Created by YuanYuan on 2016/5/13.
 */
@DatabaseTable(tableName = "physicall_location")
public class PhsicallLocation {
    @DatabaseField(id = true)
    int id;
    private List<PhyicallLocation_info> phyicallLocation_infoList;
    private Page page;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<PhyicallLocation_info> getPhyicallLocation_infoList() {
        return phyicallLocation_infoList;
    }

    public void setPhyicallLocation_infoList(List<PhyicallLocation_info> phyicallLocation_infoList) {
        this.phyicallLocation_infoList = phyicallLocation_infoList;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}

package freelancer.gcsnuoc.sharepref.entity;

import freelancer.gcsnuoc.sharepref.baseSharedPref.anonation.KeyType;
import freelancer.gcsnuoc.sharepref.baseSharedPref.anonation.Params;
import freelancer.gcsnuoc.sharepref.baseSharedPref.anonation.SharePref;
import freelancer.gcsnuoc.sharepref.baseSharedPref.anonation.TYPE;

/**
 * Created by VinhNB on 12/6/2017.
 */

@SharePref(name = "BookActivitySharePref")
public class BookActivitySharePref {
    @KeyType(name = "isFilteringBottomMenu", TYPE = TYPE.BOOLEAN)
    public boolean isFilteringBottomMenu;

    public BookActivitySharePref(@Params(name = "isFilteringBottomMenu") boolean isFilteringBottomMenu) {
        this.isFilteringBottomMenu = isFilteringBottomMenu;
    }
}

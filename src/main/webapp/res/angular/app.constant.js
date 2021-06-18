var KEY = {
    LOCAL:{
        enum_blood:"enum_blood",
        enum_discount:"enum_discount",
        enum_feature:"enum_feature",
        enum_gender:"enum_gender",
        enum_item_group:"enum_item_group",
        enum_item_type:"enum_item_type",
        enum_marital:"enum_marital",
        enum_payment_method:"enum_payment_method",
        enum_refer:"enum_refer",
        enum_relation:"enum_relation",
        enum_service_payment_state:"enum_service_payment_state",
        enum_hospital_type:"enum_hospital_type",
        enum_resident_type:"enum_resident_type",
        
        item_list:"item_list",
        instrument_list:"instrument_list",
        menu:"menu",
        features:"features",
        
        refer_by_list:"refer_by_list",
        refer_from_list:"refer_from_list",
        user_list:"user_list",
        hospital_list:"hospital_list",
        
        newly_registered_patient:"newly_registered_patient",
        new_mobile:"new_mobile",
        
        location_division:"location_division",
        location_district:"location_district",
        location_upazilla:"location_upazilla",
        location_union:"location_union",
        location_village:"location_village",
        location_rmo:"location_rmo",
        location_municipality:"location_municipality",
        
        selected_district:"selected_district",
        selected_upazilla:"selected_upazilla",
        
        medicine_company:"medicine_company",
        medicine_generic:"medicine_generic",
        medicine_brand:"medicine_brand",
        medicine_item:"medicine_item",
        medicine_form:"medicine_form",
    }
};

var CORE_API = {
    ENUM:{
        enum_blood: _baseurl_ + "core/enum/get/enum_blood",
        enum_discount: _baseurl_ + "core/enum/get/enum_discount",
        enum_feature: _baseurl_ + "core/enum/get/enum_feature",
        enum_gender: _baseurl_ + "core/enum/get/enum_gender",
        enum_marital: _baseurl_ + "core/enum/get/enum_marital",
        enum_payment_method: _baseurl_ + "core/enum/get/enum_payment_method",
        enum_relation: _baseurl_ + "core/enum/get/enum_relation",
        enum_item_group: _baseurl_ + "core/enum/get/enum_item_group",
        enum_item_type: _baseurl_ + "core/enum/get/enum_item_type",
        enum_refer: _baseurl_ + "core/enum/get/enum_refer",
        enum_service_payment_state: _baseurl_ + "core/enum/get/enum_service_payment_state",
        enum_hospital_type: _baseurl_ + "core/enum/get/enum_hospital_type",
        enum_resident_type: _baseurl_ + "core/enum/get/enum_resident_type",
    },
    SYNC:{
        item_list: _baseurl_ + "core/sync/item/all",
        instrument_list: _baseurl_ + "core/sync/instrument/all",
        
        refer_by_list: _baseurl_ + "core/sync/refer/all",
        refer_from_list: _baseurl_ + "core/sync/refer/all",
        user_list: _baseurl_ + "core/sync/user/all",
        hospital_list: _baseurl_ + "core/sync/hospital/all",
        
        
        location_division: _baseurl_ + "core/sync/division/all",
        location_district: _baseurl_ + "core/sync/district/all",
        location_upazilla: _baseurl_ + "core/sync/upazilla/all",
        location_union: _baseurl_ + "core/sync/union/all",
        location_village: _baseurl_ + "core/sync/village/all",
        location_rmo: _baseurl_ + "core/sync/rmo/all",
        location_municipality: _baseurl_ + "core/sync/municipality/all",
        
        
        medicine_company: _baseurl_ + "core/sync/medicine_company",
        medicine_generic: _baseurl_ + "core/sync/medicine_generic",
        medicine_item: _baseurl_ + "core/sync/medicine_item",
        medicine_form: _baseurl_ + "core/sync/medicine_form",
        
    }
};


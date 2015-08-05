/* Load this script using conditional IE comments if you need to support IE 7 and IE 6. */

window.onload = function() {
	function addIcon(el, entity) {
		var html = el.innerHTML;
		el.innerHTML = '<span style="font-family: \'map-icons\'">' + entity + '</span>' + html;
	}
	var icons = {
			'map-icon-map-pin' : '&#xe000;',
			'map-icon-expand' : '&#xe001;',
			'map-icon-fullscreen' : '&#xe002;',
			'map-icon-square-pin' : '&#xe003;',
			'map-icon-route-pin' : '&#xe004;',
			'map-icon-sheild' : '&#xe005;',
			'map-icon-liquor_store' : '&#xe006;',
			'map-icon-bicycle_store' : '&#xe007;',
			'map-icon-hardware_store' : '&#xe008;',
			'map-icon-insurance_agency' : '&#xe009;',
			'map-icon-lawyer' : '&#xe00a;',
			'map-icon-real_estate_agency' : '&#xe00b;',
			'map-icon-art_gallery' : '&#xe00c;',
			'map-icon-campground' : '&#xe00d;',
			'map-icon-bakery' : '&#xe00e;',
			'map-icon-bar' : '&#xe00f;',
			'map-icon-amusement_park' : '&#xe010;',
			'map-icon-aquarium' : '&#xe011;',
			'map-icon-airport' : '&#xe012;',
			'map-icon-bank' : '&#xe013;',
			'map-icon-car_rental' : '&#xe016;',
			'map-icon-car_dealer' : '&#xe017;',
			'map-icon-hospital' : '&#xe018;',
			'map-icon-hair_care' : '&#xe019;',
			'map-icon-gym' : '&#xe01a;',
			'map-icon-grocery_or_supermarket' : '&#xe01b;',
			'map-icon-general_contractor' : '&#xe01c;',
			'map-icon-pharmacy' : '&#xe01d;',
			'map-icon-point_of_interest' : '&#xe01e;',
			'map-icon-political' : '&#xe01f;',
			'map-icon-post_box' : '&#xe020;',
			'map-icon-health' : '&#xe021;',
			'map-icon-post_office' : '&#xe022;',
			'map-icon-real_estate_agency-copy' : '&#xe023;',
			'map-icon-hindu_temple' : '&#xe024;',
			'map-icon-restaurant' : '&#xe025;',
			'map-icon-female' : '&#xe026;',
			'map-icon-male' : '&#xe027;',
			'map-icon-zoo' : '&#xe028;',
			'map-icon-veterinary_care' : '&#xe029;',
			'map-icon-car_repair' : '&#xe02a;',
			'map-icon-university' : '&#xe02b;',
			'map-icon-travel_agency' : '&#xe02c;',
			'map-icon-transit_station' : '&#xe02d;',
			'map-icon-beauty_salon' : '&#xe02e;',
			'map-icon-electronics_store' : '&#xe02f;',
			'map-icon-search' : '&#xe030;',
			'map-icon-zoom-out-alt' : '&#xe031;',
			'map-icon-movie_rental' : '&#xe032;',
			'map-icon-atm' : '&#xe033;',
			'map-icon-jewelry_store' : '&#xe034;',
			'map-icon-car_wash' : '&#xe035;',
			'map-icon-unisex' : '&#xe036;',
			'map-icon-rv_park' : '&#xe037;',
			'map-icon-school' : '&#xe038;',
			'map-icon-clothing_store' : '&#xe039;',
			'map-icon-laundry' : '&#xe03a;',
			'map-icon-casino' : '&#xe03b;',
			'map-icon-place_of_worship' : '&#xe03c;',
			'map-icon-furniture_store' : '&#xe03d;',
			'map-icon-zoom-in-alt' : '&#xe03e;',
			'map-icon-zoom-in' : '&#xe03f;',
			'map-icon-department_store' : '&#xe040;',
			'map-icon-fire_station' : '&#xe041;',
			'map-icon-church' : '&#xe042;',
			'map-icon-library' : '&#xe043;',
			'map-icon-shopping_mall' : '&#xe044;',
			'map-icon-local_government' : '&#xe045;',
			'map-icon-spa' : '&#xe046;',
			'map-icon-convenience_store' : '&#xe047;',
			'map-icon-police' : '&#xe048;',
			'map-icon-route' : '&#xe049;',
			'map-icon-zoom-out' : '&#xe04a;',
			'map-icon-location-arrow' : '&#xe04b;',
			'map-icon-postal_code' : '&#xe04c;',
			'map-icon-locksmith' : '&#xe04d;',
			'map-icon-doctor' : '&#xe04e;',
			'map-icon-mosque' : '&#xe04f;',
			'map-icon-stadium' : '&#xe050;',
			'map-icon-storage' : '&#xe051;',
			'map-icon-movie_theater' : '&#xe052;',
			'map-icon-electrician' : '&#xe053;',
			'map-icon-moving_company' : '&#xe054;',
			'map-icon-postal_code_prefix' : '&#xe055;',
			'map-icon-crosshairs' : '&#xe056;',
			'map-icon-compass' : '&#xe057;',
			'map-icon-dentist' : '&#xe058;',
			'map-icon-plumber' : '&#xe059;',
			'map-icon-museum' : '&#xe05b;',
			'map-icon-finance' : '&#xe05a;',
			'map-icon-parking' : '&#xe05c;',
			'map-icon-courthouse' : '&#xe05d;',
			'map-icon-accounting' : '&#xe05e;',
			'map-icon-store' : '&#xe05f;',
			'map-icon-subway_station' : '&#xe060;',
			'map-icon-natural_feature' : '&#xe061;',
			'map-icon-florist' : '&#xe062;',
			'map-icon-food' : '&#xe063;',
			'map-icon-night_club' : '&#xe064;',
			'map-icon-synagogue' : '&#xe065;',
			'map-icon-taxi_stand' : '&#xe066;',
			'map-icon-painter' : '&#xe067;',
			'map-icon-train_station' : '&#xe068;',
			'map-icon-pet_store' : '&#xe069;',
			'map-icon-gas_station' : '&#xe06a;',
			'map-icon-funeral_home' : '&#xe06b;',
			'map-icon-cemetery' : '&#xe06c;',
			'map-icon-bowling_alley' : '&#xe06d;',
			'map-icon-roofing_contractor' : '&#xe06e;',
			'map-icon-physiotherapist' : '&#xe06f;',
			'map-icon-embassy' : '&#xe070;',
			'map-icon-city_hall' : '&#xe071;',
			'map-icon-bus_station' : '&#xe072;',
			'map-icon-park' : '&#xe073;',
			'map-icon-lodging' : '&#xe074;',
			'map-icon-toilet' : '&#xe075;',
			'map-icon-circle' : '&#xe076;',
			'map-icon-square-rounded' : '&#xe077;',
			'map-icon-square' : '&#xe078;',
			'map-icon-book_store' : '&#xe014;',
			'map-icon-cafe' : '&#xe015;',
			'map-icon-wheelchair' : '&#xe079;',
			'map-icon-volume-control-telephone' : '&#xe07a;',
			'map-icon-sign-language' : '&#xe07b;',
			'map-icon-low-vision-access' : '&#xe07c;',
			'map-icon-open-captioning' : '&#xe07d;',
			'map-icon-closed-captioning' : '&#xe07e;',
			'map-icon-braille' : '&#xe07f;',
			'map-icon-audio-description' : '&#xe080;',
			'map-icon-assistive-listening-system' : '&#xe081;',
			'map-icon-abseiling' : '&#xe082;',
			'map-icon-tennis' : '&#xe083;',
			'map-icon-skateboarding' : '&#xe084;',
			'map-icon-playground' : '&#xe085;',
			'map-icon-inline-skating' : '&#xe086;',
			'map-icon-hang-gliding' : '&#xe087;',
			'map-icon-climbing' : '&#xe088;',
			'map-icon-baseball' : '&#xe089;',
			'map-icon-archery' : '&#xe08a;',
			'map-icon-wind-surfing' : '&#xe08b;',
			'map-icon-scuba-diving' : '&#xe08c;',
			'map-icon-sailing' : '&#xe08d;',
			'map-icon-marina' : '&#xe08e;',
			'map-icon-canoe' : '&#xe08f;',
			'map-icon-boat-tour' : '&#xe090;',
			'map-icon-boat-ramp' : '&#xe091;',
			'map-icon-swimming' : '&#xe092;',
			'map-icon-whale-watching' : '&#xe093;',
			'map-icon-waterskiing' : '&#xe094;',
			'map-icon-surfing' : '&#xe095;',
			'map-icon-rafting' : '&#xe096;',
			'map-icon-kayaking' : '&#xe097;',
			'map-icon-jet-skiing' : '&#xe099;',
			'map-icon-fishing-pier' : '&#xe09a;',
			'map-icon-fish-cleaning' : '&#xe09b;',
			'map-icon-diving' : '&#xe09c;',
			'map-icon-boating' : '&#xe09d;',
			'map-icon-fishing' : '&#xe098;',
			'map-icon-cross-country-skiing' : '&#xe09e;',
			'map-icon-skiing' : '&#xe09f;',
			'map-icon-snowmobile' : '&#xe0a0;',
			'map-icon-snowboarding' : '&#xe0a1;',
			'map-icon-snow' : '&#xe0a2;',
			'map-icon-snow-shoeing' : '&#xe0a3;',
			'map-icon-sledding' : '&#xe0a4;',
			'map-icon-ski-jumping' : '&#xe0a5;',
			'map-icon-ice-skating' : '&#xe0a6;',
			'map-icon-ice-fishing' : '&#xe0a7;',
			'map-icon-chairlift' : '&#xe0a8;',
			'map-icon-golf' : '&#xe0a9;',
			'map-icon-horse-riding' : '&#xe0aa;',
			'map-icon-motobike-trail' : '&#xe0ab;',
			'map-icon-trail-walking' : '&#xe0ac;',
			'map-icon-viewing' : '&#xe0ad;',
			'map-icon-walking' : '&#xe0ae;',
			'map-icon-bicycling' : '&#xe0af;'
		},
		els = document.getElementsByTagName('*'),
		i, attr, c, el;
	for (i = 0; ; i += 1) {
		el = els[i];
		if(!el) {
			break;
		}
		attr = el.getAttribute('data-icon');
		if (attr) {
			addIcon(el, attr);
		}
		c = el.className;
		c = c.match(/map-icon-[^\s'"]+/);
		if (c && icons[c[0]]) {
			addIcon(el, icons[c[0]]);
		}
	}
};
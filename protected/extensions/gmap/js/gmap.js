GMap = function(id, address, label)
{
	this.id = id;
	this.address = address;
	this.label = typeof label == "undefined" ? address : label;
	this.geocoder = new GClientGeocoder();
}
GMap.prototype = {
	id:'',
	map:'',
	geocoder:'',
	address:'',
	marker:'',
	label:'',
	show:function()
	{
		if (this.address.indexOf(" ") !== -1)
		{
			this.address.replace(/\s/, "+");
		}
		this.geocoder.getLatLng(
		    this.address,
			function(point)
			{
				if (!point) 
				{
					m.showDefaultMap();
				}
				else
				{
					m.showMap(point);
				}
			}
		);
	},
	setAddress:function(address)
	{
		if (typeof address == "object")
		{
			var a = address;
			this.address = a["address"]+", "+
							  a["city"]+
							  (typeof a["state"] == "undefined" ? "": ", "+a["state"])+
							  (typeof a["country"] == "undefined" ? "" : ", "+a["country"])+
							  (typeof a["zip"] == "undefined" ? "" : ", "+a["zip"]);
		}
		else
		{
			this.address = address;
		}
	},
	setLabel:function(l)
	{
		this.label = l;
	},
	showDefaultMap:function()
	{
		this.map.cetCenter(new GLatLng(GMAP_DEF_LAT,GMAP_DEF_LNG), GMAP_DEF_ZOOM);
	},
	showMap:function(point)
	{
		if (typeof this.map != 'object')
		{
			this.map = new GMap2(document.getElementById(this.id));
		}
		this.map.setCenter(point, 13);
		if (typeof this.marker != "object")
		{
			this.marker = new GMarker(point);
			this.map.addOverlay(this.marker);
			this.marker.openInfoWindowHtml(this.label||this.address);
		}
		else
		{
			this.marker.setLatLng(point);
			this.marker.openInfoWindowHtml(this.label||this.address);
		}
	}
};
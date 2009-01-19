<?php
class GMap extends CWidget
{
	/**
	 * ID of the <div> element created fro the map
	 *
	 * @var string
	 */
	public $id;
	/**
	 * Google key to access API
	 *
	 * @var unknown_type
	 */
	public $key;
	/**
	 * Text to write on the marker's label
	 *
	 * @var string
	 */
	public $label;
	/**
	 * Default latitude tocenter map at
	 *
	 * @var double
	 */
	public $lat;
	/**
	 * Defalt longitude to center map at
	 *
	 * @var double
	 */
	public $lng;
	/**
	 * Default zoom when centering the map (0..17)
	 *
	 * @var int
	 */
	public $zoom;
	/**
	 * Error message to display if nothing was found
	 *
	 * @var string
	 */
	public $errorMessage;
	/**
	 * Holds address of the place
	 *
	 * @var string
	 */
	private $_address;
		
	/**
	 * Sets address for the widget
	 *
	 * @param string|array $address If string passed, it should be ADDRESS (building # and stgreet name), CITY, STATE (if applicable), COUNTRY. If array passed, it should contain keys:
	 * - address
	 * - city
	 * - state (optional)
	 * - country (optional)
	 * - zip (optional)
	 * @return unknown
	 */
	public function setAddress($a)
	{
		if (is_array($a))
		{
			$this->_address = $a['address'].', '.
							  $a['city'].
							  (empty($a['state']) ? '': ', '.$a['state']).
							  (empty($a['country']) ? '' : ', '.$a['country']).
							  (empty($a['zip']) ? '' : ', '.$a['zip']);
		}
		else 
		{
			$this->_address = $a;
		}
	}
	
	/**
	 * Rust returnds address of the widget
	 *
	 * @return unknown
	 */
	public function getAddress()
	{
		return $this->_address;
	}
	
	public function init()
	{
		//translation (Yii::t()) may be added here if needed
		if (empty($this->errorMessage)) $this->errorMessage = 'Sorry, location is not found';
		//initial app was created to sho UK, so the following should be editied for another location.
		//see instructions about this here: http://code.google.com/apis/maps/articles/yourfirstmap.html#section3
		if (empty($this->lat)) $this->lat = 54.863963;
		if (empty($this->lng)) $this->lng = 1.73584;
		if (empty($this->zoom)) $this->zoom = 7;
		
		$cs = Yii::app()->clientScript;
		$cssFile = CHtml::asset(dirname(__FILE__).DIRECTORY_SEPARATOR.'css'.DIRECTORY_SEPARATOR.'gmap.css');
		$cs->registerCssFile($cssFile);
		//we can't register google JS file as asset because it should be placed
		//into some definite folder (API Key depends on it)
		//so we have to render it in the run() method
		//And we also can't register our JS file as an asset because it should 
		//be loaded _after_ google library (errors occur if done vice versa)
	}
	
	public function run()
	{
		echo '<div id="'.$this->id.'"></div>
<script src="http://maps.google.com/maps?file=api&amp;v=2.x&amp;key='.$this->key.'" type="text/javascript"></script>
<script src="'.CHtml::asset(dirname(__FILE__).DIRECTORY_SEPARATOR.'js'.DIRECTORY_SEPARATOR.'gmap.js').'" type="text/javascript"></script>
<script language="javascript">
'.$this->js.'
</script>';
	}
	
	/**
	 * Renders the contents of the <script> tag
	 *
	 * @return string
	 */
	public function getJs()
	{
		$ret = '
var GMAP_DEF_LAT = '.$this->lat.';
var GMAP_DEF_LNG = '.$this->lng.';
var GMAP_DEF_ZOOM = '.$this->zoom.';';
		if (!empty($this->_address))
		{
			$ret .= '
var m = new GMap("'.$this->id.'", "'.str_replace(' ', '+',$this->address).'", "'.str_replace('"', '\"', $this->label).'");
m.show();';
		}
		else 
		{
			$ret .= '
var m = new GMap("'.$this->id.'", "", "'.str_replace('"', '\"', $this->label).'");';
		}
	return $ret;
	}
}
?>
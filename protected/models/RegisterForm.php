<?php 
/**
 * RegisterForm class.
 * RegisterForm is the data structure for keeping
 * user profile form data. It is used by the 'profile' action of 'SiteController'.
 */
class RegisterForm extends CActiveRecord {

	public $username;
	public $password;
	public $password2;
	public $firstname;
	public $lastname;
	public $email;
	
	public static function model($className=__CLASS__)
    {
        return parent::model($className);
    }
    
	public function rules()
    {
        return array(
            array('username, password, password2, firstname, lastname, email', 'required'),
            array('username', 'length', 'min'=>3, 'max'=>12),
            array('password', 'compare', 'compareAttribute'=>'password2'),
            array('email','email')
        );
    }
	public function attributeLabels()
	{
		return array(
			'username'=>'Insert your username',
			'password' => 'Choose your password',
			'password2' => 'Repeat your password'
		);
	}
	
	public function relations()
    {
        return array(
            'registration'=>array(self::HAS_ONE, 'ProfileForm', 'reg_id')
        );
    }
    
    //public function beforeSave()
    //{
    	// check that the new registered user is not already in database
    //}
    
    public function afterSave()
    {
    	// Insert the related row in ProfileForm
		$profile = new ProfileForm();
		$profile->reg_id = $this->id;
		$profile->save();
    }
	
}

?>
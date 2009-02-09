<?php 
/**
 * ProfileForm class.
 * ProfileForm is the data structure for keeping
 * user profile form data. It is used by the 'profile' action of 'SiteController'.
 */
class ProfileForm extends CActiveRecord {
	
	public $username;
	public $password;
	public $password2;
	public $email;
	public $firstname;
	public $lastname;
	public $reg_id;
	public $sex;
	public $age;
	public $website;
	public $create_date;
	public $update_date;
	
	public static function model($className=__CLASS__)
    {
        return parent::model($className);
    }
    
	public function rules()
    {
        return array(
            array('reg_id', 'required')
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
	
}
?>
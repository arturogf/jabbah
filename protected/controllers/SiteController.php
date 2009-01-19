<?php

class SiteController extends CController
{
	/**
	 * Declares class-based actions.
	 */
	public function actions()
	{
		return array(
			// captcha action renders the CAPTCHA image
			'captcha'=>array(
				'class'=>'CCaptchaAction',
				'backColor'=>0xEBF4FB,
			),
		);
	}

	/**
	 * This is the default 'index' action that is invoked
	 * when an action is not explicitly requested by users.
	 */
	public function actionIndex()
	{
		// renders the view file 'protected/views/site/index.php'
		// using the default layout 'protected/views/layouts/main.php'
		$this->render('index');
	}

	
	public function actiontodo()
	{
		// renders the view file 'protected/views/site/index.php'
		// using the default layout 'protected/views/layouts/main.php'
		$this->render('todo');
	}
	
	public function actionregister()
	{
	$register = new RegisterForm;
		// collect user input data
		if(isset($_POST['RegisterForm']))
		{
			$register->attributes=$_POST['RegisterForm'];
			// validate user input and redirect to previous page if valid
			if($register->validate()) {
				$register->save();
				//$this->redirect(Yii::app()->user->returnUrl);
			}
		}
		// renders the view file 'protected/views/site/profile.php'
		// using the default layout 'protected/views/layouts/main.php'		
		
		$this->render('register',array('register'=>$register));
		
		
	}
	
	public function actionprofile()
	{
	$profile = new ProfileForm;
		// collect user input data
		if(isset($_POST['ProfileForm']))
		{
			$profile->attributes=$_POST['ProfileForm'];
			// validate user input and redirect to previous page if valid
			if($profile->validate()) {
				$profile->save();
				$this->redirect(Yii::app()->user->returnUrl);
			}
		}
		// renders the view file 'protected/views/site/profile.php'
		// using the default layout 'protected/views/layouts/main.php'		
		
		$this->render('profile',array('profile'=>$profile));
		
		
	}
	
	public function actionblog()
	{
		$this->redirect('http://beejapii.wordpress.com');
	}

	/**
	 * Displays a login form to login a user.
	 */
	public function actionLogin()
	{
		$user=new LoginForm;
		// collect user input data
		if(isset($_POST['LoginForm']))
		{
			$user->attributes=$_POST['LoginForm'];
			// validate user input and redirect to previous page if valid
			if($user->validate())
				$this->redirect(Yii::app()->user->returnUrl);
		}
		// display the login form
		$this->render('login',array('user'=>$user));
	}

	/**
	 * Logout the current user and redirect to homepage.
	 */
	public function actionLogout()
	{
		Yii::app()->user->logout();
		$this->redirect(Yii::app()->homeUrl);
	}
}
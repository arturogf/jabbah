<?php

// uncomment the following to define a path alias
// Yii::setPathOfAlias('local','path/to/local-folder');

// This is the main Web application configuration. Any writable
// CWebApplication properties can be configured here.
return array(
	'basePath'=>dirname(__FILE__).DIRECTORY_SEPARATOR.'..',
	'name'=>'Don\'t worry, Beejapii!',
	'charset' => 'UTF-8',
	
	// autoloading model and component classes
	'import'=>array(
		'application.models.*',
		'application.components.*',
	),

	// application components
	'components'=>array(
		'user'=>array(
			// enable cookie-based authentication
			'allowAutoLogin'=>true,
		),
		// uncomment the following to set up database
		'db'=>array(
			'class'=>'CDbConnection',
			'connectionString'=>'mysql:host=www.gonzalez-ferrer.com;dbname=d295717_beejapii',
			'username'=>'d295717',
            'password'=>'942173',
		),
		
	),
);
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/2000/REC-xhtml1-200000126/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="language" content="en" />
<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->request->baseUrl; ?>/css/main.css" />
<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->request->baseUrl; ?>/css/form.css" />
<title><?php echo $this->pageTitle; ?></title>
</head>

<body>
<div id="page">
<div id="header">
<div id="topmenu">
<?php $this->widget('application.components.TopMenu',array(
	'items'=>array(
		array('label'=>'What\'s new', 'url'=>array('site/whatsnew'), 'visible'=>!Yii::app()->user->isGuest),
		array('label'=>'Blog', 'url'=>array('site/blog'), 'visible'=>Yii::app()->user->isGuest),
		array('label'=>'To Do', 'url'=>array('site/todo'), 'visible'=>Yii::app()->user->isGuest),
		array('label'=>'Log Out', 'url'=>array('site/logout'), 'visible'=>!Yii::app()->user->isGuest)
	),
)); ?>
</div>
<div id="logo"><?php echo CHtml::image("images/beejapii2.png","beejapii!",array("width"=>"225px")); ?></div>
<div id="mainmenu">
<?php $this->widget('application.components.MainMenu',array(
	'items'=>array(
		array('label'=>'Home', 'url'=>array('site/index')),
		array('label'=>'Login', 'url'=>array('site/login'), 'visible'=>Yii::app()->user->isGuest),
		array('label'=>'Profile', 'url'=>array('site/profile'), 'visible'=>!Yii::app()->user->isGuest),
		array('label'=>'Contacts', 'url'=>array('site/contacts'), 'visible'=>!Yii::app()->user->isGuest),
		array('label'=>'Groups', 'url'=>array('site/groups'), 'visible'=>!Yii::app()->user->isGuest),
		array('label'=>'Share Expertise', 'url'=>array('site/share'), 'visible'=>!Yii::app()->user->isGuest)
		),
)); ?>
</div><!-- mainmenu -->
</div><!-- header -->

<div id="content">
<?php echo $content; ?>
</div><!-- content -->

<div id="footer">
<p>
23 de Diciembre de 2008: con Beejapii podrás encontrar cosas que te hacen la vida más fácil.
</p>
<?php echo CHtml::image("images/yii-powered.png","yii-powered"); ?>
</div><!-- footer -->

</div><!-- page -->
</body>

</html>
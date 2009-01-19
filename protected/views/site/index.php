<?php 

if (!Yii::app()->user->isGuest) {
//	echo CHtml::tag('h2',array(),"Bienvenido, ".Yii::app()->user->name, true);	
	
	$command = Yii::app()->db->createCommand("SELECT * from Fortunes order by RAND() limit 1");
	$dataReader=$command->query(); 
	
	foreach($dataReader as $row) {
			echo CHtml::tag('h3',array(),"'".utf8_encode($row["phrase"])."'", true);	
			echo CHtml::tag('i',array(),utf8_encode($row["author"]), true);
	}
	
}
	
?>

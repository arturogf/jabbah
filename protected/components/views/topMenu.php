<ul>
<?php foreach($items as $item): ?>
<li><?php echo CHtml::link($item['label'],$item['url'],
	$item['TopActive'] ? array('class'=>'TopActive') : array()); ?></li>
<?php endforeach; ?>
</ul>

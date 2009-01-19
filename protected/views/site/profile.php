<h2>Profile</h2>

<div class="yiiForm">
<?php echo CHtml::form(); ?>

<?php echo CHtml::errorSummary($profile); ?>

<div class="simple">
<?php echo CHtml::activeLabel($profile,'username'); ?>
<?php echo CHtml::activeTextField($profile,'username') ?>
<p class="hint">
You may use username longer than 3 characters and shorter than 12.
</p>
</div>

<div class="simple">
<?php echo CHtml::activeLabel($profile,'password'); ?>
<?php echo CHtml::activePasswordField($profile,'password'); ?>
</div>

<div class="simple">
<?php echo CHtml::activeLabel($profile,'password2'); ?>
<?php echo CHtml::activePasswordField($profile,'password2'); ?>
</div>

<div class="simple">
<?php echo CHtml::activeLabel($profile,'email'); ?>
<?php echo CHtml::activeTextField($profile,'email'); ?>
</div>
<p class="hint">
Your e-mail address must be real, so that you get beejapii feedback.
</p>


<div class="simple">
<?php echo CHtml::activeLabel($profile,'firstname'); ?>
<?php echo CHtml::activeTextField($profile,'firstname'); ?>
</div>

<div class="simple">
<?php echo CHtml::activeLabel($profile,'lastname'); ?>
<?php echo CHtml::activeTextField($profile,'lastname'); ?>
</div>

<div class="simple">
<?php echo CHtml::activeLabel($profile,'sex'); ?>
<?php echo CHtml::activeDropDownList($profile,'sex',array('Selecciona una opción','Hombre','Mujer','Prefiero no decirlo')); ?>
</div>

<div class="simple">
<?php echo CHtml::activeLabel($profile,'age'); ?>
<?php echo CHtml::activeTextField($profile,'age'); ?>
</div>
<p class="hint">
Please introduce age in years.
</p>
<div class="action"></div>
<?php echo CHtml::submitButton('Save'); ?>
</div>

</form>
</div><!-- yiiForm -->

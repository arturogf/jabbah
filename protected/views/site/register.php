<h2>Create your account</h2>

<div class="yiiForm">
<?php echo CHtml::form(); ?>

<?php echo CHtml::errorSummary($register); ?>

<div class="simple">
<?php echo CHtml::activeLabel($register,'firstname'); ?>
<?php echo CHtml::activeTextField($register,'firstname'); ?>
</div>

<div class="simple">
<?php echo CHtml::activeLabel($register,'lastname'); ?>
<?php echo CHtml::activeTextField($register,'lastname'); ?>
</div>

<div class="simple">
<?php echo CHtml::activeLabel($register,'username'); ?>
<?php echo CHtml::activeTextField($register,'username') ?>
<p class="hint">
You may use username longer than 3 characters and shorter than 12.
</p>
</div>

<div class="simple">
<?php echo CHtml::activeLabel($register,'password'); ?>
<?php echo CHtml::activePasswordField($register,'password'); ?>
</div>

<div class="simple">
<?php echo CHtml::activeLabel($register,'password2'); ?>
<?php echo CHtml::activePasswordField($register,'password2'); ?>
</div>

<div class="simple">
<?php echo CHtml::activeLabel($register,'email'); ?>
<?php echo CHtml::activeTextField($register,'email'); ?>
</div>
<p class="hint">
Your e-mail address must be real, so that you get beejapii feedback.
</p>

</p>
<div class="action"></div>
<?php echo CHtml::submitButton('Save'); ?>
</div>

</form>
</div><!-- yiiForm -->

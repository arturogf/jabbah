<h2>
	Lots of <?php echo Yii::app()->name; ?> ideas...
</h2>

<ul>
<li>Intentar identificar y suavizar/eliminar/solucionar/canalizar preocupaciones, problemas y motivos de stress del usuario.</li>
<li>Hacer felices a los usuarios, y si están suscritos aún más :-)</li>
<li>Contabilizar el número de problemas resueltos a través de beejapii, a partir de cierto número puede interesar una subscripción al servicio.</li>
<li>Hacer un tour por las diferentes "features" de la aplicación. No es necesario identificarse.</li>
<li>Poner un "cómo se pronuncia beejapii" con lindos ejemplos de audio</li>.
<li>Ponerle un nombre gracioso a cualquier cosa molesta, problema, frustación, inconveniente: "Tengo un antibee", "Tengo un beetrou", "Tengo un troubee" (trouble to bee happy)</li>
<li>Crear un sistema de puntuación para los usuarios, donde sean premiados por otros usuarios o el propio sistema: los que aporten conocimiento, los que aporten felicidad, los que hagan felices a otros usuarios</li>
<li>En inglés, jugar con la palabra "be(e)tray" (traicionar, engañar)</li>
<li>AMIGO INVISIBLE INTERNACIONAL: Imagina que te haría feliz tener, qué digo yo, una camiseta de Los Celtics de Boston, y eres de Granada (Spain). Si resulta que a un chico/a de Boston, Massachussets
le interesara tener, digo yo, una camiseta de la Universidad de Granada, porque estuvo aquí de erasmus, podríais haceros felices mútuamente. Beejapii debería facilitar este tipo de contactos.</li>
</ul>

<h3>What's Next</h3>
<ul>
	<li>Implement new actions in <tt>SiteController</tt>, and create corresponding views	under <?php echo Yii::app()->viewPath . DIRECTORY_SEPARATOR . 'site'; ?></li>
	<li>Create new controllers and actions manually or using the <tt>yiic</tt> tool.
	<li>If your Web application should be driven by database, do the following:
		<ul>
			<li>Set up database connection by configuring the <code>db</code> component in the application configuration
			<tt><?php echo Yii::app()->basePath . DIRECTORY_SEPARATOR . 'config' . DIRECTORY_SEPARATOR .'main.php'; ?></tt></li>
			<li>Create model classes under the directory
			<tt><?php echo Yii::app()->basePath . DIRECTORY_SEPARATOR . 'models'; ?></tt></li>
			<li>Implement CRUD operations for a model class. For example, for the <tt>Post</tt> model class,
			you would create a <tt>PostController</tt> class together with <tt>create</tt>, <tt>read</tt>,
			<tt>update</tt> and <tt>delete</tt> actions.</li>
		</ul>
		Note, the <tt>yiic</tt> tool can automate the task of creating model classes and CRUD operations.
	</li>
</ul>

<?php if ( have_posts() ) { the_post(); rewind_posts(); } ?>
<?php if('neighborhoods' == get_post_type()){ ?>
        <?php include(TEMPLATEPATH . '/single-neighborhoods.php'); ?> 
<?php } else { ?>
	<?php include(TEMPLATEPATH . '/single-default.php'); ?> 
<?php } ?>
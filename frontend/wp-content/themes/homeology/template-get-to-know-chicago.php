<?php
/**
 * Template Name: Get to Know Chicago Template
 *
 * This is the template that displays all the neighborhoods
 *
 * @package dazzling
 */
get_header(); ?>

<link rel="stylesheet" type="text/css" href="<?php echo get_template_directory_uri(); ?>/inc/css/animate.css">
<section id="chicago" class="intro">
	<div class="gradient">
		<div class="container">
			<div class="row">
				<h1><?php the_title(); ?></h1>
			</div>
		</div>
	</div>
</section>
<div id="content" class="site-content container">
	<div id="primary" class="content-area col-sm-12 col-md-12">
		<main id="main" class="site-main" role="main">

			<?php if(have_posts()) : while(have_posts()) : the_post(); ?>
			<div class="entry-content">
				<section id="" class="wow fadeInUp" data-wow-duration="2s">
					<?php the_content(); ?>
				</section>
				<section id="" class="wow fadeInUp" data-wow-duration="2s">
					<?php the_field('people'); ?>
				</section>
				<section class="wow fadeInUp" data-wow-duration="2s">
					<?php the_field('economics'); ?>
				</section>
				<section class="wow fadeInUp" data-wow-duration="2s">
					<?php the_field('cultural'); ?>
				</section>
				<section class="wow fadeInUp" data-wow-duration="2s">
					<?php the_field('architectural'); ?>
				</section>
				<section class="wow fadeInUp" data-wow-duration="2s">
					<?php the_field('resources'); ?>
				</section>
				<?php endwhile; endif; ?>	
			</div><!-- .entry-content -->

		</main><!-- #main -->
	</div><!-- #primary -->
</div><!-- #content -->
<script>
 new WOW().init();
</script>
<?php
	get_footer();
?>
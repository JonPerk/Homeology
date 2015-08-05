<?php
/**
 * Template Name: Questionnaire Template
 *
 * This is the template that displays full width page without sidebar
 *
 * @package dazzling
 */

get_header(); ?>
<section id="questionnaire" class="intro">
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
			<div class="lead col-lg-12 col-md-12 col-sm-12 col-xs-12 ">
				<h2><?php the_field( 'headline') ?><h2>
				<p><?php the_field( 'questionnaire_description') ?></p>
			</div>

			<?php while ( have_posts() ) : the_post(); ?>

				<?php get_template_part( 'content', 'page' ); ?>

			<?php endwhile; // end of the loop. ?>
			

		</main><!-- #main -->
	</div><!-- #primary -->
</div><!-- #content -->
<?php get_footer(); ?>


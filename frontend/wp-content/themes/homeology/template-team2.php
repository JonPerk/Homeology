<?php

/**
 * Template Name: Team 2
 * This is the template that displays all the neighborhoods
 * @package dazzling
 */

get_header(); ?>

<link rel="stylesheet" type="text/css" href="<?php echo get_template_directory_uri(); ?>/inc/css/animate.css">

<section id="meet-our-team" class="intro">

	<div class="gradient">

		<div class="container">

			<div class="row">

				<h1><?php the_title(); ?></h1>

			</div>

		</div>

	</div>

</section>

<div class="site-content">

	<?php while ( have_posts() ) : the_post(); ?>

		<div class="entry-content">

			<?php the_content(); ?>

			<?php

				wp_link_pages( array(

					'before' => '<div class="page-links">' . __( 'Pages:', 'dazzling' ),

					'after'  => '</div>',

				) );

			?>

		</div><!-- .entry-content -->

	<?php endwhile; // end of the loop. ?>

	

	<section class="tutorial-scroll">

		<div id="primary" class="content-area">

			<div class="wow fadeInUp" data-wow-duration="2s">

				<div class="step-module">

					<div class="container">

						<div class="row">

							<div class="col-md-7 col-md-push-5 col-sm-7 col-sm-push-5 col-xs-12">

								<div class="step-details">

									<div class="step-title">

										<h3><?php the_field( 'step_title_1') ?></h3>

									</div>

									<div class="step-description">

										<p><?php the_field( 'step_description_1') ?></p>

									</div>

								</div>

							</div>

							<div class="col-md-5 col-md-pull-7 col-sm-5 col-sm-pull-7 col-xs-12">

								<div class="step-image">

									<img src="<?php the_field('image_step_1'); ?>" alt="step_one_image" />

								</div>

							</div>

						</div>

					</div>

				</div>

			</div>

			<div class="wow fadeInUp" data-wow-duration="2s">

				<div class="step-module">

					<div class="container">

						<div class="row">

							<div class="col-md-7 col-sm-7">

								<div class="step-details">

									<div class="step-title">

										<h3><?php the_field( 'step_title_2') ?></h3>

									</div>

									<div class="step-description">

										<p><?php the_field( 'step_description_2') ?></p>

									</div>

								</div>

							</div>

							<div class="col-md-5 col-sm-5">

								<div class="step-image">

									<img src="<?php the_field('image_step_2'); ?>" alt="step_two_image" />

								</div>

							</div>

						</div>

					</div>

				</div>

			</div>

			<div class="wow fadeInUp" data-wow-duration="2s">

				<div class="step-module">

					<div class="container">

						<div class="row">

							<div class="col-md-7 col-md-push-5 col-sm-7 col-sm-push-5 col-xs-12">

								<div class="step-details">

									<div class="step-title">

										<h3><?php the_field( 'step_title_3') ?></h3>

									</div>

									<div class="step-description">

										<p><?php the_field( 'step_description_3') ?></p>

									</div>

								</div>

							</div>

							<div class="col-md-5 col-md-pull-7 col-sm-5 col-sm-pull-7 col-xs-12">

								<div class="step-image">

									<img src="<?php the_field('image_step_3'); ?>" alt="step_three_image" />

								</div>

							</div>

						</div>

					</div>

				</div>

			</div>

	<!-- #primary -->

	</section>

</div><!-- #content -->

<script>

 new WOW().init();

</script>

<?php dazzling_call_for_action(); ?>

<?php

	get_footer();

?>
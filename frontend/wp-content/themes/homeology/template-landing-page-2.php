<?php
/**
 * Template Name: Landing Page Template 2
 *
 * This is the template that displays full width page without sidebar
 *
 * @package dazzling
 */

get_header(); ?>

<section id="who-we-are" class="intro">
	<div class="gradient">
		<div class="container">
			<div class="row">
				<h1><?php the_title(); ?></h1>
			</div>
		</div>
	</div>
</section>
<section id="lead-who-we-are" class="section">
	<div class="section_content">
		<div class="container">
			<?php while ( have_posts() ) : the_post(); ?>
			<div class="row">
				<div class="lead col-lg-12 col-md-12 col-sm-12 col-xs-12 ">
					<h2><?php the_field('h2_text'); ?></h2>
					<p class="lead-text"><?php the_field('lead'); ?></p>
				</div>
				<div class="blurb col-lg-4 col-md-4 col-sm-12 col-xs-12 ">
					<div class="like-you">
						<?php $image = wp_get_attachment_image_src(get_field('image_1'), 'full'); ?>
						<div class="blurb-img"><img src="<?php echo $image[0]; ?>" alt="<?php echo get_the_title(get_field('image_1')) ?>" /></div>
						<h4><?php the_field('header_1'); ?></h3>
						<p><?php the_field('blurb_1'); ?></p>
					</div>
				</div>
				<div class="blurb col-lg-4 col-md-4 col-sm-12 col-xs-12 ">
					<div class="live-work">
						<?php $image = wp_get_attachment_image_src(get_field('image_2'), 'full'); ?>
						<div class="blurb-img"><img src="<?php echo $image[0]; ?>" alt="<?php echo get_the_title(get_field('image_2')) ?>" /></div>
						<h4><?php the_field('header_2'); ?></h3>
						<p><?php the_field('blurb_2'); ?></p>
					</div>
				</div>
				<div class="blurb col-lg-4 col-md-4 col-sm-12 col-xs-12 ">
					<div class="passionate">
						<?php $image = wp_get_attachment_image_src(get_field('image_3'), 'full'); ?>
						<div class="blurb-img"><img src="<?php echo $image[0]; ?>" alt="<?php echo get_the_title(get_field('image_3')) ?>" /></div>
						<h4><?php the_field('header_3'); ?></h3>
						<p><?php the_field('blurb_3'); ?></p>
					</div>
				</div>
			</div>
			<?php endwhile; // end of the loop. ?>
		</div>
	</div>
</section>
<div class="cfa">
	<div class="container">
		<div class="col-md-8">
			<span class="cfa-text"><?php the_field( 'cta_message' );?></span>
		</div>
		<div class="col-md-4">
			<a class="btn btn-lg cfa-button" href="<?php the_field( 'cta_link' );?>">Contact Us</a>
		</div>
	</div>
</div>
<section class="section" id="difference">

	<div class="section_content">
		<div class="container">
			<div class="row">
				<div class="lead col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<h2><?php the_field('d_header') ?></h2>
					<p><?php the_field('d_subheader') ?></p>
					<div class="col-md-10 col-md-push-1 col-sm-10 col-sm-push-1 col-xs-12 narrow-box-container">
						<div class="row lead-row">
							<div class="col-md-4 col-sm-4 col-xs-12">
								<img class="img-limit" src="<?php the_field('d1_image'); ?>" alt="difference_one_image" />
							</div>
							<div class="lead-body col-md-8 col-sm-8 col-xs-12">
								<h4><?php the_field('d1_header') ?></h4>
								<p><?php the_field('d1_body_1') ?></p>
								<p><?php the_field('d1_body_2') ?></p>
								<p><?php the_field('d1_body_3') ?></p>
							</div>
						</div>
						<div class="row lead-row">
							<div class="col-md-4 col-sm-4 col-xs-12">
								<img class="img-limit" src="<?php the_field('d2_image'); ?>" alt="difference_one_image" />
							</div>
							<div class="lead-body col-md-8 col-sm-8 col-xs-12">
								<h4><?php the_field('d2_header') ?></h4>
								<p><?php the_field('d2_body_1') ?></p>
								<p><?php the_field('d2_body_2') ?></p>
								<p><?php the_field('d2_body_3') ?></p>
							</div>
						</div>
						<div class="row lead-row">
							<div class="col-md-4 col-sm-4 col-xs-12">
								<img class="img-limit" src="<?php the_field('d3_image'); ?>" alt="difference_three_image" />	
							</div>
							<div class="lead-body col-md-8 col-sm-8 col-xs-12">
								<h4><?php the_field('d3_header') ?></h4>
								<p><?php the_field('d3_body_1') ?></p>
								<p><?php the_field('d3_body_2') ?></p>
								<p><?php the_field('d3_body_3') ?></p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<div class="cfa">
	<div class="container">
		<div class="col-md-8">
			<span class="cfa-text"><?php the_field( 'cta_message' );?></span>
		</div>
		<div class="col-md-4">
			<a class="btn btn-lg cfa-button" href="<?php the_field( 'cta_link' );?>">Contact Us</a>
		</div>
	</div>
</div>
<?php get_footer(); ?>



<?php
/**
 * Template Name: Landing Page Template
 *
 * This is the template that displays all the neighborhoods
 *
 * @package dazzling
 */

get_header(); ?>
<?php if( function_exists('cyclone_slider') ) cyclone_slider('home-page-slider'); ?>
			<div class="cfa">
				<div class="container">
					<div class="col-md-8">
						<span class="cfa-text"><?php the_field( 'cta_message' );?></span>
					</div>
					<div class="col-md-4">
						<a class="btn btn-lg cfa-button" href="<?php the_field( 'cta_link' );?>">Get Started</a>
					</div>
				</div>
			</div>
			<section class="section">
				<div class="section_content">
					<div class="container">
						<?php 
						$module1 = get_field('b1_icon'); 
						$module2 = get_field('b2_icon');
						$module3 = get_field('b3_icon');
			
						?>
						<div class="row">
							<div class="lead">
								<h2><?php the_field( 'headline' );?></h2>
								<p><?php the_field( 'sub_header' );?></p>
							</div>
						</div>
						<div class="row">
						
							<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
								<div class="module module_callout" href="#">
									<div class="blurb-img">
										<img src="<?php echo $module1['url']; ?>" alt="Community Focus Image">
									</div>
									<h3 class="module_title"><?php the_field( 'b1_title' );?></h3>

									<div class="module_body">
										<p><?php the_field( 'b1_body' );?></p>
									</div>
								</div>
							</div>
							<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
								<div class="module module_callout" href="#">
									<div class="blurb-img">
										<img src="<?php echo $module2['url']; ?>" alt="Relocation Support Image">
									</div>

									<h3 class="module_title"><?php the_field( 'b2_title' );?></h3>

									<div class="module_body">
										<p><?php the_field( 'b2_body' );?></p>
									</div>
								</div>
							</div>
							<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
								<div class="module module_callout" href="#">
									<div class="blurb-img">
										<img src="<?php echo $module3['url']; ?>" alt="Convenience Image">
									</div>

									<h3 class="module_title"><?php the_field( 'b3_title' );?></h3>

									<div class="module_body">
										<p><?php the_field( 'b3_body' );?></p>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
			<section class="section" id="features">
				<div class="section_content">
					<div class="container">
						<div class="row">
							<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<h2><?php the_field('module_header'); ?></h2>
								<div class="row">
									<div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
										<div class="module_icon">
											<img src="<?php the_field('module_icon_1'); ?>" alt="Commute Icon">
										</div>
									</div>
									<div class="col-lg-10 col-md-10 col-sm-9 col-xs-9">
										<h3><?php the_field('module_title_1'); ?></h3>
										<div class="module_body">
											<p><?php the_field('module_body_1'); ?></p>
										</div>
									</div>
								</div>
								<hr>
								<div class="row">
									<div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
										<div class="module_icon">
											<img src="<?php the_field('module_icon_2'); ?>" alt="Budget Icon">
										</div>
									</div>
									<div class="col-lg-10 col-md-10 col-sm-9 col-xs-9">
										<h3><?php the_field('module_title_2'); ?></h3>
										<div class="module_body">
											<p><?php the_field('module_body_2'); ?></p>
										</div>
									</div>
								</div>
								<hr>
								<div class="row">
									<div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
										<div class="module_icon">
											<img src="<?php the_field('module_icon_3'); ?>" alt="Amenities Icon">
										</div>
									</div>
									<div class="col-lg-10 col-md-10 col-sm-9 col-xs-9">
										<h3><?php the_field('module_title_3'); ?></h3>
										<div class="module_body">
											<p><?php the_field('module_body_3'); ?></p>
										</div>
									</div>
								</div>
							</div>
							<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<img src="<?php the_field( 'features_image' ); ?>" alt="Features Image">
							</div>
						</div>
					</div>
				</div>
			</section>
			<section class="section" data-section="awards" id="ref-awards">
				<div class="section_content">
					<div class="container">
						<div class="row">
							<div class="col-md-8 col-md-offset-2">
								<p class="home-testimonial"><?php the_field( 'home_testimonial' );?></p>
							</div>
						</div>	
						<div class="row">
							<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
								<div>
									<a class="award" href="<?php the_field( 'ar1_link' );?>">
										<?php $image = wp_get_attachment_image_src(get_field('ar1_image'), 'full'); ?>
										<img src="<?php echo $image[0]; ?>" alt="<?php echo get_the_title(get_field('ar1_image')) ?>" />
									</a>
								</div>
							</div>
							<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
								<div>
									<a class="award" href="<?php the_field( 'ar2_link' );?>">
										<?php $image = wp_get_attachment_image_src(get_field('ar2_image'), 'full'); ?>
										<img src="<?php echo $image[0]; ?>" alt="<?php echo get_the_title(get_field('ar2_image')) ?>" />
									</a>
								</div>
							</div>
							<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
								<div>
									<a class="award" href="<?php the_field( 'ar3_link' );?>">
										<?php $image = wp_get_attachment_image_src(get_field('ar3_image'), 'full'); ?>
										<img src="<?php echo $image[0]; ?>" alt="<?php echo get_the_title(get_field('ar3_image')) ?>" />
									</a>
								</div>
							</div>
							<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
								<div>
									<a class="award" href="<?php the_field( 'ar4_link' );?>">
										<?php $image = wp_get_attachment_image_src(get_field('ar4_image'), 'full'); ?>
										<img src="<?php echo $image[0]; ?>" alt="<?php echo get_the_title(get_field('ar4_image')) ?>" />
									</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
			<section id="testimonials" class="section">
				<div class="section_content">
					<div class="container">
						<div class="row">
							<div id="home-testimonials" class="col-md-8 col-md-offset-2">
								<?php the_content(); ?>
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
						<a class="btn btn-lg cfa-button" href="<?php the_field( 'cta_link' );?>">Get Started</a>
					</div>
				</div>
			</div>
<?php get_footer(); ?>
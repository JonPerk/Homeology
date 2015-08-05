<?php
/**
 * Template Name: Neighborhood Tour Landing Page Template
 *
 * This is the template that displays all the neighborhoods
 *
 * @package dazzling
 */
get_header(); ?>
<section id="neighborhood-tour" class="intro">
	<div class="gradient">
		<div class="container">
			<div class="row">
				<h1><?php the_title(); ?></h1>
			</div>
		</div>
	</div>
</section>
<section id="split-nav">
<div id="content" class="site-content container">
	<div id="primary" class="content-area col-sm-12 col-md-12">
		<main id="main" class="site-main" role="main">
			<div class="col-lg-12 col-md-12">	
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
					<a href="<?php the_field( 'left_image_link' );?>" class="col" data-col="suburb">
				
						<img src="<?php the_field('left_image'); ?>" alt="<?php echo get_the_title(get_field('left_image')) ?>">

						<div class="sub-title"><h3><?php the_field('left_title');?></h3></div>

						<div class="module_body">
							<p><?php the_field( 'b1_body' );?></p>
						</div>
					</a>
				</div>
				<div class="col-lg-46 col-md-6 col-sm-6 col-xs-12">
					<a href="<?php the_field( 'right_image_link' );?>" class="col" data-col="city">
						<img src="<?php the_field('right_image'); ?>" alt="<?php echo get_the_title(get_field('right_image')) ?>">

						<div class="sub-title"><h3><?php the_field('right_title');?></h3></div>

						<div class="module_body">
							<p><?php the_field( 'b3_body' );?></p>
						</div>
					</a>
				</div>
			</div>
		</main>
	</div>
</div>
</section>
<?php get_footer();?>
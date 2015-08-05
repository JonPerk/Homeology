<?php
/**
 * The Template for displaying all single posts.
 *
 * @package dazzling
 */

get_header(); ?>

<div class="hero">
    <div class="hero-img-wrap">
    	<?php the_post_thumbnail( 'dazzling-featured' ); ?>
    </div>
    <div class="container">
		<div class="hero_content hero_content_bottom">
			<div class="hero_content_title">
			
				<header>
					<h1 class="entry-title "><?php the_title(); ?></h1>
				</header>
			
			</div>
		</div>
    </div>
</div>
<div id="content" class="site-content container">
	<div id="primary" class="content-area col-sm-12 col-md-12 <?php echo of_get_option( 'site_layout', 'no entry' ); ?>">
		<main id="main" class="site-main" role="main">
			<?php if(have_posts()) : while(have_posts()) : the_post(); ?>
				<article id="post-<?php the_ID(); ?>" <?php post_class(); ?>>
					

					<div class="entry-content">
						<div class="row headline">
							<h2><?php the_field('neighborhood_headline'); ?></h2>
						</div>
						<div class="row">
							
							<div id="neighborhood-info" class="container col-sm-12 col-md-6 col-lg-6">
								<?php the_content(); ?>
								<?php if (get_the_tags()) :?>
									<div class="tags">
										<p>Tags:</p>
										<?php the_tags('', ' ', ''); ?>
									</div>
								<?php endif;?>
							</div>
							<div id="neighborhood-map" class="col-sm-12 col-md-6 col-lg-6">
								<?php the_field('neighborhood_map'); ?>
							</div>
							
						</div>
						<div class="row">
							<div class="col-sm-12 col-md-12">
								<?php the_field('neighborhood_data_table'); ?>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 col-md-12 col-lg-12 ftb-container">
								<?php the_field('ftb_data'); ?>
							</div>
						</div>						
						<?php
							wp_link_pages( array( 
								'before'            => '<div class="page-links">'.__( 'Pages:', 'dazzling' ),
								'after'             => '</div>',
								'link_before'       => '<span>',
								'link_after'        => '</span>',
								'pagelink'          => '%',
								'echo'              => 1 
							) );
						?>
					</div><!-- .entry-content -->

					<footer class="entry-meta">
						

						<?php edit_post_link( __( 'Edit', 'dazzling' ), '<i class="fa fa-pencil-square-o"></i><span class="edit-link">', '</span>' ); ?>
						<?php dazzling_setPostViews(get_the_ID()); ?>
						<hr class="section-divider">
					</footer><!-- .entry-meta -->
				</article><!-- #post-## -->
			<?php endwhile; endif; ?>
		</main><!-- #main -->
	</div><!-- #primary -->
	<!-- <div id="secondary" class="widget-area col-sm-12 col-md-4" role="complementary">
		<aside id="search" class="widget widget_search">
			<?php get_search_form(); ?>
		</aside>
		<aside id="archives" class="widget">
			<h3 class="widget-title"><?php _e( 'Neighborhood Categories', 'dazzling' ); ?></h1>
		</aside>
	</div>#secondary -->
</div><!-- #content -->
<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^https:/.test(d.location)?'https':'http';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+"://cdn1.findthebest.com/rx/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","ftb-widgetjs");</script>
<!-- 
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>

<script>
$(document).ready(function(){
  $('p.tags a').wrap('<span class="st_tag" />');
});
</script>
 -->
<?php get_footer(); ?>
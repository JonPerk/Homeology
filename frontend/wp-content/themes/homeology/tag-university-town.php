<?php
/**
 * Tag Template for "University Town"
 *
 * This is the template that displays all the neighborhoods
 *
 * @package dazzling
 */

get_header(); ?>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
<script type="text/javascript" src="http://auvidazen.com/homeology/build005/wp-content/themes/homeology/inc/js/jquery.sticky.js?ver=4.1"></script>
<script>
    $(window).load(function(){
      $("#sticky-nav").sticky({ topSpacing: 0 });
    });
</script>
<section id="tag-template" class="intro">
	<div class="gradient">
		<div class="container">
			<div class="row">
				<h1><?php the_title(); ?></h1>
			</div>
		</div>
	</div>
</section>
<section id="secondary-nav">
	<div id="sticky-nav" class="filter-container">
		<div class="container">
			<div class="col-sm-12 col-md-12">
				
				<ul id="sticky-menu" class="load-portfolio">
					<li class="filter-label">
						<i class="fa fa-map-marker"> Filter by Region</i>
					</li>
					<li class="active"><span><a href="#" class="all">All</a></span></li>
					<?php
						$args = array( 'taxonomy' => 'nregion' );
						$terms = get_terms( 'nregion', $args );
						$count = count( $terms );
						if ( $count > 0 ) :
							foreach ( $terms as $term ) :
								$term_list .= '<li><span><a href="#" class="'. $term->slug .'">' . $term->name . '</a></span></li>';
							endforeach;
							echo $term_list;
						endif;
					?>
				</ul>
			</div>
		</div>
	</div>
</section>
<div id="content" class="site-content container">
	
	<div id="primary" class="content-area col-sm-12 col-md-12">
		<main id="main" class="site-main" role="main">
			
			<div class="site-wrap">
				<div class="portfolio-grid">
				<?php
					$args2 = array(
						'post_type' => 'neighborhoods',
						'posts_per_page'   => '-1',
						'orderby'    => 'title',
						'order'      => 'ASC',
						'tag'  => 'university-town',
					);
					$tag_tax = new WP_Query( $args2 );
					while ( $tag_tax->have_posts() ) : $tag_tax->the_post();
				?>
				
				<article id="post-<?php the_ID(); ?>" <?php post_class(); ?>>
					<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12" data-id="post-<?php the_ID();?>" data-type="<?php echo custom_taxonomy_terms_slugs( 'nregion' ); ?>">
						<?php if ( has_post_thumbnail()) : ?>
							<a href="<?php the_permalink(); ?>" title="<?php the_title_attribute(); ?>" >
								<?php the_post_thumbnail( 'dazzling-featured', array( 'class' => 'thumbnail col-sm-12' )); ?>
							</a>
						<?php endif; ?>
						<div class="entry-header-neighborhood">
							<h4><a href="<?php the_permalink(); ?>" rel="bookmark"><?php the_title(); ?></a></h4>	
							
							<div class="entry-meta">

							<?php if ( 'post' == get_post_type() ) : // Hide category and tag text for pages on Search ?>
								<?php
									/* translators: used between list items, there is a space after the comma */
									$categories_list = get_the_category_list( __( ', ', 'dazzling' ) );
									if ( $categories_list && dazzling_categorized_blog() ) :
								?>
								<span class="cat-links"><i class="fa fa-folder-open-o"></i>
									<?php printf( __( ' %1$s', 'dazzling' ), $categories_list ); ?>
								</span>
								<?php endif; // End if categories ?>
							<?php endif; // End if 'post' == get_post_type() ?>

							<!-- <?php edit_post_link( __( 'Edit', 'dazzling' ), '<i class="fa fa-pencil-square-o"></i><span class="edit-link">', '</span>' ); ?> -->

							</div><!-- .entry-meta -->
						</div><!-- .entry-header -->
					</div>
				</article><!-- #post-## -->
				
				<?php endwhile;?>

				</div>
			</div>
			
		</main><!-- #main -->
	</div><!-- #primary -->
</div><!-- #content -->
<?php get_footer(); ?>
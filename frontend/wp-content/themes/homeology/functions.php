<?php
/**
 * _s functions and definitions
 *
 * @package homeology
 */

/**
 * Add Custom Neighborhood Custom Post Type
 */

function register_cpt_neighborhoods() {
 
    $labels = array( 
  		'name'               => __( 'Neighborhoods', 'text_domain' ),
		'singular_name'      => __( 'Neighborhood', 'text_domain' ),
		'add_new'            => _x( 'Add New Neighborhood', '${4:Name}', 'text_domain' ),
		'add_new_item'       => __( 'Add New Neighborhood', 'text_domain}' ),
		'edit_item'          => __( 'Edit Neighborhood', 'text_domain' ),
		'new_item'           => __( 'New Neighborhood', 'text_domain' ),
		'view_item'          => __( 'View Neighborhood', 'text_domain' ),
		'search_items'       => __( 'Search Neighborhoods', 'text_domain' ),
		'not_found'          => __( 'No Neighborhoods found', 'text_domain' ),
		'not_found_in_trash' => __( 'No Neighborhoods found in Trash', 'text_domain' ),
		'parent_item_colon'  => __( 'Parent Neighborhood:', 'text_domain' ),
		'menu_name'          => __( 'Neighborhoods', 'text_domain' ),
    );
 
    $args = array( 
		'labels'              => $labels,
		'hierarchical'        => true,
		'description'         => 'description',
		'taxonomies'          => array( 'category', 'post_tag'),
		'public'              => true,
		'show_ui'             => true,
		'show_in_menu'        => true,
		'menu_position'       => 5,
		//'menu_icon'         => '',
		'show_in_nav_menus'   => true,
		'publicly_queryable'  => true,
		'exclude_from_search' => false,
		'has_archive'         => true,
		'query_var'           => true,
		'can_export'          => true,
		'rewrite'             => true,
		'capability_type'     => 'post', 
		'supports'            => array( 
									'title', 'editor', 'thumbnail', 'post-formats'
								),
    );
    register_post_type( 'Neighborhoods', $args );
    flush_rewrite_rules( false );
}
add_action( 'init', 'register_cpt_neighborhoods' );

/**
 * Add Tag Classes
 */
function wpb_tags() { 
$wpbtags =  get_tags();
foreach ($wpbtags as $tag) { 
$string .= '<span class="tagbox"><a class="taglink" href="'. get_tag_link($tag->term_id) .'">'. $tag->name . '</a><span class="tagcount">'. $tag->count .'</span></span>' . "\n"   ;
} 
return $string;
} 
add_shortcode('wpbtags' , 'wpb_tags' );

/**
 * Quicksand
 */
register_taxonomy("nregion", array("neighborhoods"), array("hierarchical" => true,"label" => "Neighborhood Regions", "singular_label" => "Neighborhood Region"));
// Create a list of taxonomy slugs
function custom_taxonomy_terms_slugs( $taxonomy ){
  $terms = get_the_terms( $post->ID, $taxonomy );

  if ( $terms && ! is_wp_error( $terms ) ) : 

    $term_slugs = array();

    foreach ( $terms as $term ) {
        $term_slugs[] = $term->slug;
    }

    $termslugs = join( " ", $term_slugs );

    return $termslugs;
    endif;
}
add_action( 'init', 'custom_taxonomy_terms_slugs', 0 );
/** End Quicksand */

/**
 * Load Tutorial Scroll.js
 */
function wowscript() {
if ( !is_admin() ) { // this if statement will insure the following code only gets added to your wp site and not the admin page cause your code has no business in the admin page right unless that's your intentions
	// jquery
		wp_register_script('wowjs', ( get_bloginfo('template_url') . '/inc/js/wow.min.js'), array('jquery')); // this last part-( array('jquery') )is added in case your script needs to be included after jquery
		wp_enqueue_script('wowjs'); // then print. it will be added after jquery is added
	}
}
add_action( 'wp_print_scripts', 'wowscript'); // now just run the function

/** End Load Tutorial Scroll */

// For tag lists on tag archives: Returns other tags except the current one (redundant)
function tag_ur_it($glue) {
    $current_tag = single_tag_title( '', '',  false );
    $separator = "n";
    $tags = explode( $separator, get_the_tag_list( "", "$separator", "" ) );
    foreach ( $tags as $i => $str ) {
        if ( strstr( $str, ">$current_tag<" ) ) {
            unset($tags[$i]);
            break;
        }
    }
    if ( empty($tags) )
        return false;
 
    return trim(join( $glue, $tags ));
} // end tag_ur_it

/**
 * Set the content width based on the theme's design and stylesheet.
 */
if ( ! isset( $content_width ) ) {
	$content_width = 730; /* pixels */
}

if ( ! function_exists( 'dazzling_setup' ) ) :
/**
 * Sets up theme defaults and registers support for various WordPress features.
 *
 * Note that this function is hooked into the after_setup_theme hook, which
 * runs before the init hook. The init hook is too late for some features, such
 * as indicating support for post thumbnails.
 */
function dazzling_setup() {

	/*
	 * Make theme available for translation.
	 * Translations can be filed in the /languages/ directory.
	 * If you're building a theme based on _s, use a find and replace
	 * to change 'dazzling' to the name of your theme in all the template files
	 */
	load_theme_textdomain( 'dazzling', get_template_directory() . '/languages' );

	// Add default posts and comments RSS feed links to head.
	add_theme_support( 'automatic-feed-links' );

	/*
	 * Enable support for Post Thumbnails on posts and pages.
	 *
	 * @link http://codex.wordpress.org/Function_Reference/add_theme_support#Post_Thumbnails
	 */
	add_theme_support( 'post-thumbnails' );

  add_image_size( 'dazzling-featured', 730, 410, true );
	add_image_size( 'tab-small', 60, 60 , true); // Small Thumbnail

	// This theme uses wp_nav_menu() in one location.
	register_nav_menus( array(
		'primary' => __( 'Primary Menu', 'dazzling' ),
		'footer-links' => __( 'Footer Links', 'dazzling' ) // secondary nav in footer
	) );

	// Enable support for Post Formats.
	add_theme_support( 'post-formats', array( 'aside', 'image', 'video', 'quote', 'link' ) );

	// Setup the WordPress core custom background feature.
	add_theme_support( 'custom-background', apply_filters( 'dazzling_custom_background_args', array(
		'default-color' => 'ffffff',
		'default-image' => '',
	) ) );
}
endif; // dazzling_setup
add_action( 'after_setup_theme', 'dazzling_setup' );

/**
 * Register widgetized area and update sidebar with default widgets.
 */
function dazzling_widgets_init() {
	register_sidebar( array(
		'name'          => __( 'Sidebar', 'dazzling' ),
		'id'            => 'sidebar-1',
		'before_widget' => '<aside id="%1$s" class="widget %2$s">',
		'after_widget'  => '</aside>',
		'before_title'  => '<h3 class="widget-title">',
		'after_title'   => '</h3>',
	) );
	register_sidebar(array(
    	'id' => 'home-widget-1',
    	'name' => __( 'Homepage Widget 1', 'dazzling' ),
    	'description' => __( 'Displays on the Home Page', 'dazzling' ),
    	'before_widget' => '<div id="%1$s" class="widget %2$s">',
    	'after_widget' => '</div>',
    	'before_title' => '<h3 class="widgettitle">',
    	'after_title' => '</h3>',
    ));

    register_sidebar(array(
      'id' => 'home-widget-2',
      'name' =>  __( 'Homepage Widget 2', 'dazzling' ),
      'description' => __( 'Displays on the Home Page', 'dazzling' ),
      'before_widget' => '<div id="%1$s" class="widget %2$s">',
      'after_widget' => '</div>',
      'before_title' => '<h3 class="widgettitle">',
      'after_title' => '</h3>',
    ));

    register_sidebar(array(
      'id' => 'home-widget-3',
      'name' =>  __( 'Homepage Widget 3', 'dazzling' ),
      'description' =>  __( 'Displays on the Home Page', 'dazzling' ),
      'before_widget' => '<div id="%1$s" class="widget %2$s">',
      'after_widget' => '</div>',
      'before_title' => '<h3 class="widgettitle">',
      'after_title' => '</h3>',
    ));

    register_sidebar(array(
    	'id' => 'footer-widget-1',
    	'name' =>  __( 'Footer Widget 1', 'dazzling' ),
    	'description' =>  __( 'Used for footer widget area', 'dazzling' ),
    	'before_widget' => '<div id="%1$s" class="widget %2$s">',
    	'after_widget' => '</div>',
    	'before_title' => '<h3 class="widgettitle">',
    	'after_title' => '</h3>',
    ));

    register_sidebar(array(
      'id' => 'footer-widget-2',
      'name' =>  __( 'Footer Widget 2', 'dazzling' ),
      'description' =>  __( 'Used for footer widget area', 'dazzling' ),
      'before_widget' => '<div id="%1$s" class="widget %2$s">',
      'after_widget' => '</div>',
      'before_title' => '<h3 class="widgettitle">',
      'after_title' => '</h3>',
    ));

    register_sidebar(array(
      'id' => 'footer-widget-3',
      'name' =>  __( 'Footer Widget 3', 'dazzling' ),
      'description' =>  __( 'Used for footer widget area', 'dazzling' ),
      'before_widget' => '<div id="%1$s" class="widget %2$s">',
      'after_widget' => '</div>',
      'before_title' => '<h3 class="widgettitle">',
      'after_title' => '</h3>',
    ));


    register_widget( 'dazzling_popular_posts_widget' );
}
add_action( 'widgets_init', 'dazzling_widgets_init' );

include(get_template_directory() . "/inc/popular-posts-widget.php");


/**
 * Enqueue scripts and styles.
 */
function dazzling_scripts() {
  wp_enqueue_script('jquery');
  wp_enqueue_style( 'dazzling-bootstrap', get_template_directory_uri() . '/inc/css/bootstrap.css' );

  wp_enqueue_style( 'dazzling-icons', get_template_directory_uri().'/inc/css/font-awesome.min.css' );

  if( ( is_home() || is_front_page() ) && of_get_option('dazzling_slider_checkbox') == 1 ) {
		wp_enqueue_style( 'flexslider-css', get_template_directory_uri().'/inc/css/flexslider.css' );
  }

	wp_enqueue_style( 'dazzling-style', get_stylesheet_uri() );

	wp_enqueue_script('dazzling-bootstrapjs', get_template_directory_uri().'/inc/js/bootstrap.min.js', array('jquery') );

	if( ( is_home() || is_front_page() ) && of_get_option('dazzling_slider_checkbox') == 1 ) {
		wp_enqueue_script( 'flexslider', get_template_directory_uri() . '/inc/js/flexslider.min.js', array('jquery'), '2.2.2', true );
	}
	wp_enqueue_script( 'dazzling-main', get_template_directory_uri() . '/inc/js/main.js', array('jquery') );
  wp_enqueue_script('jquery-sticky-js', get_template_directory_uri().'/inc/js/jquery.sticky.js', array('jquery') );
}
add_action( 'wp_enqueue_scripts', 'dazzling_scripts' );

/**
 * Add HTML5 shiv and Respond.js for IE8 support of HTML5 elements and media queries
 */
function dazzling_ie_support_header() {
    echo '<!--[if lt IE 9]>'. "\n";
    echo '<script src="' . esc_url( get_template_directory_uri() . '/inc/js/html5shiv.min.js' ) . '"></script>'. "\n";
    echo '<script src="' . esc_url( get_template_directory_uri() . '/inc/js/respond.min.js' ) . '"></script>'. "\n";
    echo '<![endif]-->'. "\n";
}
add_action( 'wp_head', 'dazzling_ie_support_header', 11 );

/*
 * Loads the Options Panel
 *
 * If you're loading from a child theme use stylesheet_directory
 * instead of template_directory
 */

define('OPTIONS_FRAMEWORK_URL', get_template_directory() . '/inc/admin/');
define('OPTIONS_FRAMEWORK_DIRECTORY', get_template_directory_uri() . '/inc/admin/');
require_once (OPTIONS_FRAMEWORK_URL . 'options-framework.php');

/**
 * Implement the Custom Header feature.
 */
require get_template_directory() . '/inc/custom-header.php';

/**
 * Custom template tags for this theme.
 */
require get_template_directory() . '/inc/template-tags.php';

/**
 * Custom functions that act independently of the theme templates.
 */
require get_template_directory() . '/inc/extras.php';

/**
 * Customizer additions.
 */
require get_template_directory() . '/inc/customizer.php';

/**
 * Load Jetpack compatibility file.
 */
require get_template_directory() . '/inc/jetpack.php';

/**
 * Load custom nav walker
 */
require get_template_directory() . '/inc/navwalker.php';

if ( class_exists( 'woocommerce' ) ) {
/**
 * WooCommerce related functions
 */
require get_template_directory() . '/inc/woo-setup.php';
}

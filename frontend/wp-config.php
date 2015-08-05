<?php
/**
 * The base configurations of the WordPress.
 *
 * This file has the following configurations: MySQL settings, Table Prefix,
 * Secret Keys, WordPress Language, and ABSPATH. You can find more information
 * by visiting {@link http://codex.wordpress.org/Editing_wp-config.php Editing
 * wp-config.php} Codex page. You can get the MySQL settings from your web host.
 *
 * This file is used by the wp-config.php creation script during the
 * installation. You don't have to use the web site, you can just copy this file
 * to "wp-config.php" and fill in the values.
 *
 * @package WordPress
 */

// ** MySQL settings - You can get this info from your web host ** //
/** The name of the database for WordPress */
define('WP_CACHE', true); //Added by WP-Cache Manager
define( 'WPCACHEHOME', '/home3/urb2burb/public_html/homeologychicago.com/wp-content/plugins/wp-super-cache/' ); //Added by WP-Cache Manager

define('DB_NAME', 'urb2burb_wrdp3');

/** MySQL database username */
define('DB_USER', 'urb2burb_wrdp3');

/** MySQL database password */
define('DB_PASSWORD', '8B3490yYNDR9Zl2');

/** MySQL hostname */
define('DB_HOST', 'localhost');

/** Database Charset to use in creating database tables. */
define('DB_CHARSET', 'utf8');

/** The Database Collate type. Don't change this if in doubt. */
define('DB_COLLATE', '');

/**#@+
 * Authentication Unique Keys and Salts.
 *
 * Change these to different unique phrases!
 * You can generate these using the {@link https://api.wordpress.org/secret-key/1.1/salt/ WordPress.org secret-key service}
 * You can change these at any point in time to invalidate all existing cookies. This will force all users to have to log in again.
 *
 * @since 2.6.0
 */
define('AUTH_KEY',         'oVz4nS?S22beteUnS;/@qDIS6~ehz-dWV<?8FWIDPp6W3FC4u~(urN?g1!FU-TR~o<Z2M?6ODgF');
define('SECURE_AUTH_KEY',  '');
define('LOGGED_IN_KEY',    'BbC\`7S6Z\`)P7sKt_:9#nRv_~B:np@RqSg58!0nIWQ*vO/:)@-8XY(fM>R=*yj>xt');
define('NONCE_KEY',        'nCl\`A!<I;MTSs8-0FKV2M3lH-0=tNr7UPR2|?|8*6go01OfQiB-aOM5#auE!M:$l3eo8~^_!lDmlp|AW(3');
define('AUTH_SALT',        'D@q<PAbrJ=G-xf0^MahEzp5T*Wml15L0#GB1vcNkdE#e9a$m26Eq1hjA/FLD4DHa:BLu');
define('SECURE_AUTH_SALT', 'iS1tCiq9CD5Os1H-B7DmgdlnAnKtjAO*a~/Xh:c\`sHy;B*:$A~m>vj9NAeWB7UqIBnd~^4d9;2Ib=5L?');
define('LOGGED_IN_SALT',   'g3y/^ctmp!h:ksavY>UlNA?oWt!hLT(Vqc2Z#JIiJ$do8uU_Lc*uY$$Mc1YgEWFeeC#hb\`9A6)w');
define('NONCE_SALT',       'q~Q#-XnV6o;8q<R\`ROcEWVoi)M4TS9zybcPp~TPWkVYsD3oJ6Y:F@_YX)2?:7<T\`a:#Lu0R');

/**#@-*/

/**
 * WordPress Database Table prefix.
 *
 * You can have multiple installations in one database if you give each a unique
 * prefix. Only numbers, letters, and underscores please!
 */
$table_prefix  = 'wp_';

/**
 * WordPress Localized Language, defaults to English.
 *
 * Change this to localize WordPress. A corresponding MO file for the chosen
 * language must be installed to wp-content/languages. For example, install
 * de_DE.mo to wp-content/languages and set WPLANG to 'de_DE' to enable German
 * language support.
 */
define('WPLANG', '');

/**
 * For developers: WordPress debugging mode.
 *
 * Change this to true to enable the display of notices during development.
 * It is strongly recommended that plugin and theme developers use WP_DEBUG
 * in their development environments.
 */
define('WP_DEBUG', false);

/* That's all, stop editing! Happy blogging. */

/** Absolute path to the WordPress directory. */
if ( !defined('ABSPATH') )
	define('ABSPATH', dirname(__FILE__) . '/');

/** Sets up WordPress vars and included files. */
require_once(ABSPATH . 'wp-settings.php');

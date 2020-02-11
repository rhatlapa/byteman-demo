# Description

these examples are inspired by presentation shown in devconf 2019 (https://devconfcz2019.sched.com/event/Jcdp/debugging-with-java-runtime-decompiler-byteman) and extended for purposes of showing different capabilities of byteman

# Notes from the byteman workshop on Devconf 2019
 * byteman limitations
   * for variable rules, depends on debug table; still have parameters access
   * method must be reloaded before actions takes effect, so no direct effect to while(true){}, but ok with any subcalls (unless it got inlined)
 * surprising strengths are in manipulating ALL overriding/implementing methods
 * bm \<tab\>
   * https://developer.jboss.org/wiki/ABytemanTutorial#how_do_i_load_rules_into_a_running_program + https://developer.jboss.org/wiki/ABytemanTutorial#how_do_i_install_the_agent_into_a_running_program
   * bminstall pid
   * bminstall \`java-runtime-decompiler  -listjvms | grep Ethernal | sed "s/ .*//"\`
   * bmsubmit file
     * no error reporting!
   * bmsubmit -u file
   * bmsubmit can redefine rules, but be aware once you remove rule form file. So better to -u them always
   * bm check, useless without -cp :(
 * jrd + byteman
   * no overwhelming substitution
   * good addition
   * great for apps without debuginfo and/or obfuscated
     * ITW case
 * http://downloads.jboss.org/byteman/4.0.5/byteman-programmers-guide.html#location-specifiers + http://downloads.jboss.org/byteman/4.0.5/byteman-programmers-guide.html#rule-expressions
 * date
   * simple info obtaining
   * simple injections and fixes
 * calc
   * no debug info
   * binding
   * manipulating results
 * uniq
   * discovering obfuscated code
   * flags, countdowns
   * fixing essential methods
 * thread/dead 
   * synchronization info
 * row
   * misleading random failure
   * nasty memory leak
